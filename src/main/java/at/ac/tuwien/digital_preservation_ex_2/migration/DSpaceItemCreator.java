package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceItem;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class DSpaceItemCreator {

  private final HttpHeaders headers = new HttpHeaders();
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public DSpaceItemCreator(final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.baseUrl =
            dSpaceConfigProperties
                    .getProtocol()
                    .concat(dSpaceConfigProperties.getHost())
                    .concat(":")
                    .concat(dSpaceConfigProperties.getPort());

    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("rest-dspace-token", dSpaceConfigProperties.getAccessToken());
  }

  public DSpaceItem createItem(final int collectionId, final DSpaceItem item) {
    final String path = "/rest/collections/".concat(String.valueOf(collectionId)).concat("/items");
    final String url = baseUrl.concat(path);

    HttpEntity<DSpaceItem> entity = new HttpEntity<>(item, headers);
    ResponseEntity<DSpaceItem> result =
            restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceItem.class);
    return result.getBody();
  }
}
