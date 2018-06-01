package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DSpaceItemRetriever {

  private final HttpHeaders headers;
  private final RestTemplate restTemplate;
  private final String baseUrl;
  private final DSpaceSessionHolder dSpaceSessionHolder;

  @Autowired
  public DSpaceItemRetriever(
      final DSpaceConfigProperties dSpaceConfigProperties,
      final RestTemplate restTemplate,
      final DSpaceSessionHolder dSpaceSessionHolder) {
    this.restTemplate = restTemplate;
    this.dSpaceSessionHolder = dSpaceSessionHolder;
    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());

    headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
  }

  public List<DSpaceItem> getItems() {
    headers.add(HttpHeaders.COOKIE, dSpaceSessionHolder.getSession());
    final String path = "/rest/items";
    final String url = baseUrl.concat(path);

    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<DSpaceItem[]> result =
        restTemplate.exchange(url, HttpMethod.GET, entity, DSpaceItem[].class);
    return Arrays.asList(result.getBody());
  }
}
