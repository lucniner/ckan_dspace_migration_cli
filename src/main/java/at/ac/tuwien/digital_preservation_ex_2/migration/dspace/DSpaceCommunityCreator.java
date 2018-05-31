package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DSpaceCommunityCreator {

  private final HttpHeaders headers;
  private final String url;
  private final RestTemplate restTemplate;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  @Autowired
  public DSpaceCommunityCreator(
      final DSpaceConfigProperties dSpaceConfigProperties,
      final RestTemplate restTemplate,
      final DSpaceSessionHolder DSpaceSessionHolder) {
    this.restTemplate = restTemplate;
    this.DSpaceSessionHolder = DSpaceSessionHolder;
    final String baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());
    final String path = "/rest/communities";
    this.url = baseUrl.concat(path);

    headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
  }

  public DSpaceCommunity createCommunity(final DSpaceCommunity community) {
    headers.add(HttpHeaders.COOKIE, DSpaceSessionHolder.getSession());
    HttpEntity<DSpaceCommunity> entity = new HttpEntity<>(community, headers);

    ResponseEntity<DSpaceCommunity> result =
        restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceCommunity.class);
    return result.getBody();
  }
}
