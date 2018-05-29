package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceBitStream;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class DSpaceBitstreamCreator {

  private final HttpHeaders headers = new HttpHeaders();
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public DSpaceBitstreamCreator(final DSpaceConfigProperties dSpaceConfigProperties, final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.baseUrl =
            dSpaceConfigProperties
                    .getProtocol()
                    .concat(dSpaceConfigProperties.getHost())
                    .concat(":")
                    .concat(dSpaceConfigProperties.getPort());

    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.add("rest-dspace-token", TokenHolder.getToken());
  }

  public DSpaceBitStream create(final long itemId, final byte[] resource, final String fileName, final String mimeType) {
    final String path = "/rest/items/".concat(String.valueOf(itemId)).concat("/bitstreams?name=").concat(fileName);
    final String url = baseUrl.concat(path);
    final HttpEntity<byte[]> requestEntity = new HttpEntity<>(resource, headers);
    final ResponseEntity<DSpaceBitStream> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, DSpaceBitStream.class);
    return responseEntity.getBody();
  }
}
