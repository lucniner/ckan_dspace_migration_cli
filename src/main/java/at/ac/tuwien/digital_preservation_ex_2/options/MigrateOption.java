package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.migration.*;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.*;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigrateOption extends AbstractOption {

  private Map<String, DSpaceCommunity> communityMap = new HashMap<>();
  private Map<String, DSpaceCollection> collectionMap = new HashMap<>();
  private Map<String, DSpaceItem> itemMap = new HashMap<>();

  private final RestTemplate restTemplate;
  private final OutputStream stream;
  private final CkanConfigProperties ckanConfigProperties;
  private final DSpaceConfigProperties dSpaceConfigProperties;

  public MigrateOption(
      String optionCommand,
      String optionDescription,
      RestTemplate restTemplate,
      OutputStream stream,
      CkanConfigProperties properties,
      DSpaceConfigProperties dSpaceConfigProperties) {
    super(optionCommand, optionDescription);
    this.restTemplate = restTemplate;
    this.stream = stream;
    this.ckanConfigProperties = properties;
    this.dSpaceConfigProperties = dSpaceConfigProperties;

  }

  @Override
  public String getOptionCommand() {
    return optionCommand;
  }

  @Override
  public void executeOption() {
    migrateOrganizations();
    migratePackages();
  }

  private void migrateOrganizations() {
    SimpleCkanResult organizations = retrieveOrganizations();
    createCommunity(organizations);
  }

  private void migratePackages() {
    final CkanPackageRetriever retriever =
            new CkanPackageRetriever(ckanConfigProperties, restTemplate);
    final List<CkanPackage> ckanPackages = retriever.getPackages();
    for (final CkanPackage ckanPackage : ckanPackages) {
      handleCommunity(ckanPackage);
      handleGroups(ckanPackage);
      handleItem(ckanPackage);
    }
  }

  private void handleCommunity(final CkanPackage ckanPackage) {
    final String organizationName = ckanPackage.getOrganization().getName();
    if (communityMap.get(organizationName) == null) {
      migrateCommunity(organizationName);
    }
  }

  private void handleGroups(final CkanPackage ckanPackage) {
    final String organizationName = ckanPackage.getOrganization().getName();
    final DSpaceCommunity community = communityMap.get(organizationName);
    final CkanGroup[] groups = ckanPackage.getGroups();
    if (groups.length == 0) {
      if (collectionMap.get("default") == null) {
        migrateCollection(community.getId(), "default");
      }
    } else {
      for (final CkanGroup group : groups) {
        if (collectionMap.get(group.getName()) == null) {
          migrateCollection(community.getId(), group.getName());
        }
      }
    }
  }

  private void handleItem(final CkanPackage ckanPackage) {
    final CkanGroup[] groups = ckanPackage.getGroups();
    if (groups.length == 0) {
      final int id = collectionMap.get("default").getId();
      migrateItem(id, ckanPackage);
    } else {
      for (final CkanGroup group : groups) {
        final int id = collectionMap.get(group.getName()).getId();
        migrateItem(id, ckanPackage);
      }
    }
  }

  private void migrateItem(final int collectionId, final CkanPackage ckanPackage) {
    final DSpaceItemCreator creator = new DSpaceItemCreator(dSpaceConfigProperties, restTemplate);
    final DSpaceItem dSpaceItem = new DSpaceItem(ckanPackage.getName());
    dSpaceItem.addMetadata("dc.title", ckanPackage.getName()); //TODO set metadata
    final DSpaceItem item = creator.createItem(collectionId, dSpaceItem);
    itemMap.put(item.getName(), item);
    createBitstreams(item.getId(), ckanPackage);
  }

  private void createBitstreams(final long id, final CkanPackage ckanPackage) {
    final CkanResource[] resources = ckanPackage.getResources();
    for (final CkanResource resource : resources) {
      final byte[] content = restTemplate.getForObject(resource.getUrl(), byte[].class);
      final DSpaceBitstreamCreator bitstreamCreator = new DSpaceBitstreamCreator(dSpaceConfigProperties, restTemplate);
      bitstreamCreator.create(id, content, resource.getName(), resource.getMimetype());
    }
  }

  private SimpleCkanResult retrieveOrganizations() {
    final String baseUrl =
            ckanConfigProperties
            .getProtocol()
                    .concat(ckanConfigProperties.getHost())
            .concat(":")
                    .concat(ckanConfigProperties.getPort());
    final String path = "/api/3/action/organization_list";
    final String url = baseUrl.concat(path);
    return restTemplate.getForObject(url, SimpleCkanResult.class);
  }

  private void createCommunity(SimpleCkanResult organizations) {
    for (String organization : organizations.getResult()) {
      migrateCommunity(organization);
    }
  }

  private void migrateCommunity(final String name) {
    final DSpaceCommunityCreator dSpaceCommunityCreator =
            new DSpaceCommunityCreator(dSpaceConfigProperties, restTemplate);
    final DSpaceCommunity dSpaceCommunity = new DSpaceCommunity(null, name, "community", null);
    final DSpaceCommunity community = dSpaceCommunityCreator.createCommunity(dSpaceCommunity);
    communityMap.put(community.getName(), community);
  }

  private void migrateCollection(final int communityId, final String name) {
    final DSpaceCollectionCreator collectionCreator =
            new DSpaceCollectionCreator(dSpaceConfigProperties, restTemplate);
    final DSpaceCollection dSpaceCollection = new DSpaceCollection(null, name, "collection", null);
    final DSpaceCollection collection =
            collectionCreator.createCollection(communityId, dSpaceCollection);
    collectionMap.put(collection.getName(), collection);
  }

  @Override
  public String getOptionDescription() {
    return optionDescription;
  }
}
