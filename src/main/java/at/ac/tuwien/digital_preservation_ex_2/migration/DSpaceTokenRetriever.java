package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceUser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class DSpaceTokenRetriever {

  private final HttpHeaders headers = new HttpHeaders();
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public DSpaceTokenRetriever(
      final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());

    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
  }

  public String getToken(final DSpaceUser dSpaceUser) {
    final String path = "/rest/login";
    final String url = baseUrl.concat(path);

    HttpEntity<DSpaceUser> entity = new HttpEntity<>(dSpaceUser, headers);
    ResponseEntity<String> result =
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    return result.getBody();
  }

}
