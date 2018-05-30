package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceItem;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DSpaceItemRetriever {

  private final HttpHeaders headers = new HttpHeaders();
  private final RestTemplate restTemplate;
  private final String baseUrl;

  public DSpaceItemRetriever(
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

  public List<DSpaceItem> getItems() {
    final String path = "/rest/items";
    final String url = baseUrl.concat(path);

    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<DSpaceItem[]> result =
        restTemplate.exchange(url, HttpMethod.GET, entity, DSpaceItem[].class);
    return Arrays.asList(result.getBody());
  }
}
