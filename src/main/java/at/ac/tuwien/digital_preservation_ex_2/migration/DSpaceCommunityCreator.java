package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceCommunity;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class DSpaceCommunityCreator {

  private final HttpHeaders headers = new HttpHeaders();
  private final String url;
  private final RestTemplate restTemplate;

  public DSpaceCommunityCreator(final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;

    final String baseUrl =
            dSpaceConfigProperties
                    .getProtocol()
                    .concat(dSpaceConfigProperties.getHost())
                    .concat(":")
                    .concat(dSpaceConfigProperties.getPort());
    final String path = "/rest/communities";
    this.url = baseUrl.concat(path);

    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("rest-dspace-token", dSpaceConfigProperties.getAccessToken());
  }

  public DSpaceCommunity createCommunity(final DSpaceCommunity community) {
    HttpEntity<DSpaceCommunity> entity = new HttpEntity<>(community, headers);
    ResponseEntity<DSpaceCommunity> result =
            restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceCommunity.class);
    return result.getBody();
  }
}
