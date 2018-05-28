package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.CkanPackageResult;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceCollection;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceCommunity;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MigrateOption extends AbstractOption {

  private Map<String, DSpaceCommunity> communityMap;
  private Map<String, DSpaceCollection> collectionMap;

  private final RestTemplate restTemplate;
  private final OutputStream stream;
  private final CkanConfigProperties properties;
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
    this.properties = properties;
    this.dSpaceConfigProperties = dSpaceConfigProperties;
    this.communityMap = new HashMap<>();
    this.collectionMap = new HashMap<>();
  }

  @Override
  public String getOptionCommand() {
    return optionCommand;
  }

  @Override
  public void executeOption() {
    migrateOrganizations();
    migrateGroups();
  }

  private void migrateGroups() {
      CkanPackageResult groups = retrieveGroups();
      createCollection(groups);
  }

  private void migrateOrganizations() {
    CkanPackageResult organizations = retrieveOrganizations();
    createCommunity(organizations);
  }

  private CkanPackageResult retrieveOrganizations() {
    final String baseUrl =
        properties
            .getProtocol()
            .concat(properties.getHost())
            .concat(":")
            .concat(properties.getPort());
    final String path = "/api/3/action/organization_list";
    final String url = baseUrl.concat(path);
    return restTemplate.getForObject(url, CkanPackageResult.class);
  }

  private CkanPackageResult retrieveGroups(){
      final String baseUrl =
              properties
                      .getProtocol()
                      .concat(properties.getHost())
                      .concat(":")
                      .concat(properties.getPort());
      final String path = "/api/3/action/organization_list";
      final String url = baseUrl.concat(path);
      return restTemplate.getForObject(url, CkanPackageResult.class);
  }

  private void createCollection(CkanPackageResult groups){
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

  private void createCommunity(CkanPackageResult organizations) {
    final String baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());
    final String path = "/rest/communities";
    final String url = baseUrl.concat(path);

    for (String organization : organizations.getResult()) {
      DSpaceCommunity dSpaceCommunity = new DSpaceCommunity(null, organization, "community", null);

      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      headers.add("rest-dspace-token", dSpaceConfigProperties.getAccessToken());

      HttpEntity<DSpaceCommunity> entity = new HttpEntity<>(dSpaceCommunity, headers);

      ResponseEntity<DSpaceCommunity> result =
          restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceCommunity.class);
      DSpaceCommunity resultCommunity = result.getBody();

      communityMap.put(resultCommunity.getName(), resultCommunity);
    }
  }

  private void printResult(CkanPackageResult result) {
    try {
      stream.write(
          "The following PUBLIC data sets are available at the CKAN repository.\n".getBytes());
      final String[] results = result.getResult();
      for (int i = 0; i < results.length; i++) {
        stream.write(results[i].getBytes());
        stream.write("\n".getBytes());
      }
      stream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getOptionDescription() {
    return optionDescription;
  }
}
