package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceMetadataField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DSpaceMetadataFieldCreator {

  private final HttpHeaders headers;
  private final String url;
  private final RestTemplate restTemplate;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  @Autowired
  public DSpaceMetadataFieldCreator(
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
    final String path = "rest/registries/schema/dc/metadata-fields";
    this.url = baseUrl.concat(path);

    headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
  }

  public DSpaceMetadataField createMetadataField(final DSpaceMetadataField dSpaceMetadataField) {
    headers.add(HttpHeaders.COOKIE, DSpaceSessionHolder.getSession());
    HttpEntity<DSpaceMetadataField> entity = new HttpEntity<>(dSpaceMetadataField, headers);

    ResponseEntity<DSpaceMetadataField> result =
        restTemplate.exchange(url, HttpMethod.POST, entity, DSpaceMetadataField.class);
    return result.getBody();
  }
}
