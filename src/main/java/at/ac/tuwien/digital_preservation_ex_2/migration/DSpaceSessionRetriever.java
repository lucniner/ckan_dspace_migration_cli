package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceUser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class DSpaceSessionRetriever {

  private final HttpHeaders headers = new HttpHeaders();
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public DSpaceSessionRetriever(
      final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());
  }

  public String getSession(final DSpaceUser dSpaceUser) {

    final String path = "/rest/login";
    final String url = baseUrl.concat(path);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("email", dSpaceUser.getEmail())
            .queryParam("password", dSpaceUser.getPassword());

    System.out.println(builder.toUriString());

    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<Void> result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Void.class);

    String setCookie = result.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    String sessionId = setCookie.substring(0, setCookie.indexOf(";"));
    System.out.println(sessionId);
    return sessionId;
  }
}
