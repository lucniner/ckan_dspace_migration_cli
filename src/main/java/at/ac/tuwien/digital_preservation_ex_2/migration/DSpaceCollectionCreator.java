package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceCollection;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class DSpaceCollectionCreator {

  private final HttpHeaders headers = new HttpHeaders();
  private final RestTemplate restTemplate;
  private final String baseUrl;

  public DSpaceCollectionCreator(
      final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;

    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());

    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add(HttpHeaders.COOKIE, SessionHolder.getSession());
  }

  public DSpaceCollection createCollection(
      final String communityId, final DSpaceCollection collection) {
    final String path =
        "/rest/communities/".concat(String.valueOf(communityId)).concat("/collections");
    final String url = baseUrl.concat(path);

    HttpEntity<DSpaceCollection> entity = new HttpEntity<>(collection, headers);
    ResponseEntity<DSpaceCollection> result =
        restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceCollection.class);
    return result.getBody();
  }
}
