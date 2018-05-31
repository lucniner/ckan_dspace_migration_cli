package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DSpaceItemCreator {

  private final HttpHeaders headers;
  private final String baseUrl;
  private final RestTemplate restTemplate;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  @Autowired
  public DSpaceItemCreator(
      final DSpaceConfigProperties dSpaceConfigProperties,
      final RestTemplate restTemplate,
      final DSpaceSessionHolder DSpaceSessionHolder) {
    this.restTemplate = restTemplate;
    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());
    this.DSpaceSessionHolder = DSpaceSessionHolder;
    headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
  }

  public DSpaceItem createItem(final String collectionId, final DSpaceItem item) {
    headers.add(HttpHeaders.COOKIE, DSpaceSessionHolder.getSession());
    final String path = "/rest/collections/".concat(String.valueOf(collectionId)).concat("/items");
    final String url = baseUrl.concat(path);

    HttpEntity<DSpaceItem> entity = new HttpEntity<>(item, headers);
    ResponseEntity<DSpaceItem> result =
        restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceItem.class);
    return result.getBody();
  }
}
