package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DSpaceCollectionCreator {

  private final HttpHeaders headers;
  private final RestTemplate restTemplate;

  private final String baseUrl;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  @Autowired
  public DSpaceCollectionCreator(
      final DSpaceConfigProperties dSpaceConfigProperties,
      final RestTemplate restTemplate,
      final DSpaceSessionHolder DSpaceSessionHolder) {
    this.restTemplate = restTemplate;
    this.DSpaceSessionHolder = DSpaceSessionHolder;
    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());

    headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add(HttpHeaders.COOKIE, DSpaceSessionHolder.getSession());
  }

  public DSpaceCollection createCollection(
      final String communityId, final DSpaceCollection collection) {
    headers.add(HttpHeaders.COOKIE, DSpaceSessionHolder.getSession());
    final String path =
        "/rest/communities/".concat(String.valueOf(communityId)).concat("/collections");
    final String url = baseUrl.concat(path);

    HttpEntity<DSpaceCollection> entity = new HttpEntity<>(collection, headers);
    ResponseEntity<DSpaceCollection> result =
        restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceCollection.class);
    return result.getBody();
  }
}
