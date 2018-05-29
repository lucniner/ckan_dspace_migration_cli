package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.migration.CkanPackageRetriever;
import at.ac.tuwien.digital_preservation_ex_2.migration.DSpaceCommunityCreator;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.CkanPackage;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceCollection;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceCommunity;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.SimpleCkanResult;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigrateOption extends AbstractOption {

  private Map<String, DSpaceCommunity> communityMap;
  private Map<String, DSpaceCollection> collectionMap;

  private final RestTemplate restTemplate;
  private final OutputStream stream;
  private final CkanConfigProperties ckanConfigProperties;
  private final DSpaceConfigProperties dSpaceConfigProperties;
  private final DSpaceCommunityCreator dSpaceCommunityCreator;

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
    this.communityMap = new HashMap<>();
    this.collectionMap = new HashMap<>();
    this.dSpaceCommunityCreator = new DSpaceCommunityCreator(dSpaceConfigProperties, restTemplate);
  }

  @Override
  public String getOptionCommand() {
    return optionCommand;
  }

  @Override
  public void executeOption() {
//    migrateOrganizations();
    migratePackages();
  }

  private void migrateOrganizations() {
    SimpleCkanResult organizations = retrieveOrganizations();
    createCommunity(organizations);
  }

  private void migratePackages() {
    final CkanPackageRetriever retriever = new CkanPackageRetriever(ckanConfigProperties, restTemplate);
    final List<CkanPackage> ckanPackages = retriever.getPackages();
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

  private void createCollection(SimpleCkanResult groups) {
    final String baseUrl =
            dSpaceConfigProperties
                    .getProtocol()
                    .concat(dSpaceConfigProperties.getHost())
                    .concat(":")
                    .concat(dSpaceConfigProperties.getPort());
    final String path = "/rest/collections";
    final String url = baseUrl.concat(path);

    for (String group : groups.getResult()) {
      DSpaceCollection dSpaceCollection = new DSpaceCollection(null, group, "collection", null);

      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      headers.add("rest-dspace-token", dSpaceConfigProperties.getAccessToken());
      HttpEntity<DSpaceCollection> entity = new HttpEntity<>(dSpaceCollection, headers);
      ResponseEntity<DSpaceCollection> result =
              restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceCollection.class);
      DSpaceCollection resultCollection = result.getBody();

      collectionMap.put(resultCollection.getName(), resultCollection);
    }
  }

  private void createCommunity(SimpleCkanResult organizations) {
    for (String organization : organizations.getResult()) {
      migrateCommunity(organization);
    }
  }

  private void migrateCommunity(final String name) {
    final DSpaceCommunity dSpaceCommunity = new DSpaceCommunity(null, name, "community", null);
    final DSpaceCommunity resultCommunity = dSpaceCommunityCreator.createCommunity(dSpaceCommunity);
    communityMap.put(resultCommunity.getName(), resultCommunity);
  }

  @Override
  public String getOptionDescription() {
    return optionDescription;
  }
}
