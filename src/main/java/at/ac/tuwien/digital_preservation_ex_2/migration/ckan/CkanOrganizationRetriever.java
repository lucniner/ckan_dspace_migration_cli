package at.ac.tuwien.digital_preservation_ex_2.migration.ckan;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.SimpleCkanResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CkanOrganizationRetriever {

  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate;

  private final String baseUrl;
  private final String organizationListUrl;

  @Autowired
  public CkanOrganizationRetriever(
      final CkanConfigProperties ckanConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.objectMapper = new ObjectMapper();
    this.baseUrl =
        ckanConfigProperties
            .getProtocol()
            .concat(ckanConfigProperties.getHost())
            .concat(":")
            .concat(ckanConfigProperties.getPort());
    organizationListUrl = baseUrl.concat("/api/3/action/organization_list");
  }

  public SimpleCkanResult getOrganizations() {
    return restTemplate.getForObject(organizationListUrl, SimpleCkanResult.class);
  }
}
