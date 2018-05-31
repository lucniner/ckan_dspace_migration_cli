package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceBitStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DSpaceBitstreamCreator {

  private final HttpHeaders headers;
  private final RestTemplate restTemplate;

  private final String baseUrl;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  @Autowired
  public DSpaceBitstreamCreator(
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
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
  }

  public DSpaceBitStream create(
      final String itemId, final byte[] resource, final String fileName, final String mimeType) {
    headers.add(HttpHeaders.COOKIE, DSpaceSessionHolder.getSession());
    final String path =
        "/rest/items/".concat(String.valueOf(itemId)).concat("/bitstreams?name=").concat(fileName);
    final String url = baseUrl.concat(path);
    final HttpEntity<byte[]> requestEntity = new HttpEntity<>(resource, headers);
    final ResponseEntity<DSpaceBitStream> responseEntity =
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, DSpaceBitStream.class);
    return responseEntity.getBody();
  }
}
