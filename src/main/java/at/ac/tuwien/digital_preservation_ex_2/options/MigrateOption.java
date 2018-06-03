package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.migration.ckan.CkanFileRetriever;
import at.ac.tuwien.digital_preservation_ex_2.migration.ckan.CkanOrganizationRetriever;
import at.ac.tuwien.digital_preservation_ex_2.migration.ckan.CkanPackageRetriever;
import at.ac.tuwien.digital_preservation_ex_2.migration.dspace.*;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.*;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceCollection;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceCommunity;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceItem;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MigrateOption extends AbstractOption {

  private static final String CMD = "migrate";
  private static final String DESCRIPTION = "Migrate resources from Ckan to DSpace";

  private Map<String, DSpaceCommunity> communityMap = new HashMap<>();
  private Map<String, DSpaceCollection> collectionMap = new HashMap<>();
  private Map<String, DSpaceItem> itemMap = new HashMap<>();

  private final DSpaceMetadataFieldCreator dSpaceMetadataFieldCreator;
  private final CkanPackageRetriever ckanPackageRetriever;
  private final DSpaceItemCreator dSpaceItemCreator;
  private final DSpaceCommunityCreator dSpaceCommunityCreator;
  private final DSpaceCollectionCreator dSpaceCollectionCreator;
  private final DSpaceBitstreamCreator dSpaceBitstreamCreator;
  private final CkanOrganizationRetriever ckanOrganizationRetriever;
  private final CkanFileRetriever ckanFileRetriever;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  @Autowired
  public MigrateOption(
      final DSpaceMetadataFieldCreator dSpaceMetadataFieldCreator,
      final CkanPackageRetriever ckanPackageRetriever,
      final DSpaceItemCreator dSpaceItemCreator,
      final DSpaceCommunityCreator dSpaceCommunityCreator,
      final DSpaceCollectionCreator dSpaceCollectionCreator,
      final DSpaceBitstreamCreator dSpaceBitstreamCreator,
      final CkanOrganizationRetriever ckanOrganizationRetriever,
      final CkanFileRetriever ckanFileRetriever,
      final DSpaceSessionHolder DSpaceSessionHolder) {
    super(CMD, DESCRIPTION);
    this.dSpaceMetadataFieldCreator = dSpaceMetadataFieldCreator;
    this.ckanPackageRetriever = ckanPackageRetriever;
    this.dSpaceItemCreator = dSpaceItemCreator;
    this.dSpaceCommunityCreator = dSpaceCommunityCreator;
    this.dSpaceCollectionCreator = dSpaceCollectionCreator;
    this.dSpaceBitstreamCreator = dSpaceBitstreamCreator;
    this.ckanOrganizationRetriever = ckanOrganizationRetriever;
    this.ckanFileRetriever = ckanFileRetriever;
    this.DSpaceSessionHolder = DSpaceSessionHolder;
  }

  @Override
  public String getOptionCommand() {
    return optionCommand;
  }

  @Override
  public void executeOption() {
    if (DSpaceSessionHolder.getSession() == null) {
      System.out.println("Please login to migrate.");
      return;
    }
    migrateSchema();
    migrateOrganizations();
    migratePackages();
    printSuccess();
  }

  private void printSuccess() {
    System.out.println("Successfully migrated data");
  }

  /* Will create an internal server error, if the fields already exist. */
  private void migrateSchema() {
    dSpaceMetadataFieldCreator.createMetadataField(
        new DSpaceMetadataField(
            "dc.bitrate",
            "bitrate",
            null,
            "Use primarily for bitrate of audio and video resources."));
    dSpaceMetadataFieldCreator.createMetadataField(
        new DSpaceMetadataField(
            "dc.length", "length", null, "Use primarily for length of audio and video resources."));
  }

  private void migrateOrganizations() {
    SimpleCkanResult organizations = ckanOrganizationRetriever.getOrganizations();
    createCommunity(organizations);
  }

  private void migratePackages() {
    final List<CkanPackage> ckanPackages = ckanPackageRetriever.getPackages();
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
        migrateCollection(community.getUuid(), "default");
      }
    } else {
      for (final CkanGroup group : groups) {
        if (collectionMap.get(group.getName()) == null) {
          migrateCollection(community.getUuid(), group.getName());
        }
      }
    }
  }

  private void handleItem(final CkanPackage ckanPackage) {
    final CkanGroup[] groups = ckanPackage.getGroups();
    if (groups.length == 0) {
      final String id = collectionMap.get("default").getUuid();
      migrateItem(id, ckanPackage);
    } else {
      for (final CkanGroup group : groups) {
        final String id = collectionMap.get(group.getName()).getUuid();
        migrateItem(id, ckanPackage);
      }
    }
  }

  private void migrateItem(final String collectionId, final CkanPackage ckanPackage) {
    final DSpaceItem dSpaceItem = new DSpaceItem(ckanPackage.getName());

    dSpaceItem.addMetadata("dc.title", ckanPackage.getName());
    dSpaceItem.addMetadata("dc.subject", ckanPackage.getNotes());
    dSpaceItem.addMetadata("dc.description", ckanPackage.getResources()[0].getDescription());
    dSpaceItem.addMetadata("dc.date.issued", LocalDateTime.now().toString());
    dSpaceItem.addMetadata("dc.creator", ckanPackage.getAuthor());
    dSpaceItem.addMetadata("dc.contributor", ckanPackage.getMaintainer());
    dSpaceItem.addMetadata("dc.type", ckanPackage.getResources()[0].getFormat());
    dSpaceItem.addMetadata("dc.format", ckanPackage.getResources()[0].getMimetype());
    dSpaceItem.addMetadata("dc.rights", ckanPackage.getLicense_title());

    CkanCustomMetadata[] extras = ckanPackage.getExtras();
    for (CkanCustomMetadata customMetadata : extras) {
      String key = customMetadata.getKey();
      String value = customMetadata.getValue();
      if (key.equals("bitrate")) {
        dSpaceItem.addMetadata("dc.bitrate", value);
      } else if (key.equals("length")) {
        dSpaceItem.addMetadata("dc.length", value);
      }
    }

    final DSpaceItem item = dSpaceItemCreator.createItem(collectionId, dSpaceItem);
    itemMap.put(item.getName(), item);
    createBitstreams(item.getUuid(), ckanPackage);
  }

  private void createBitstreams(final String id, final CkanPackage ckanPackage) {
    final CkanResource[] resources = ckanPackage.getResources();
    for (final CkanResource resource : resources) {
      final byte[] content = ckanFileRetriever.getFile(resource.getUrl());
      dSpaceBitstreamCreator.create(id, content, resource.getName(), resource.getMimetype());
    }
  }

  private void createCommunity(SimpleCkanResult organizations) {
    for (String organization : organizations.getResult()) {
      migrateCommunity(organization);
    }
  }

  private void migrateCommunity(final String name) {
    final DSpaceCommunity dSpaceCommunity = new DSpaceCommunity(null, name, "community", null);
    final DSpaceCommunity community = dSpaceCommunityCreator.createCommunity(dSpaceCommunity);
    communityMap.put(community.getName(), community);
  }

  private void migrateCollection(final String communityId, final String name) {
    final DSpaceCollection dSpaceCollection = new DSpaceCollection(null, name, "collection", null);
    final DSpaceCollection collection =
        dSpaceCollectionCreator.createCollection(communityId, dSpaceCollection);
    collectionMap.put(collection.getName(), collection);
  }

  @Override
  public String getOptionDescription() {
    return optionDescription;
  }
}
