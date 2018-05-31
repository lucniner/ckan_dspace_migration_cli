package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DSpaceSessionRetriever {

  private final HttpHeaders headers;
  private final String baseUrl;
  private final RestTemplate restTemplate;

  @Autowired
  public DSpaceSessionRetriever(
      final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.baseUrl =
        dSpaceConfigProperties
            .getProtocol()
            .concat(dSpaceConfigProperties.getHost())
            .concat(":")
            .concat(dSpaceConfigProperties.getPort());
    headers = new HttpHeaders();
  }

  public String getSession(final DSpaceUser dSpaceUser) {

    final String path = "/rest/login";
    final String url = baseUrl.concat(path);

    UriComponentsBuilder builder =
        UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("email", dSpaceUser.getEmail())
            .queryParam("password", dSpaceUser.getPassword());

    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<Void> result =
        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Void.class);

    String setCookie = result.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    String sessionId = setCookie.substring(0, setCookie.indexOf(";"));
    return sessionId;
  }
}
