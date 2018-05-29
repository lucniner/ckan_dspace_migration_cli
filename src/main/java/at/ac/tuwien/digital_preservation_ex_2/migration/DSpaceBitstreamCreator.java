package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceBitStream;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.add("rest-dspace-token", dSpaceConfigProperties.getAccessToken());
  }

  public void create(final long itemId, final Resource resource, final String fileName, final String mimeType) {
    final String path = "/rest/items/".concat(String.valueOf(itemId)).concat("/bitstreams?name=").concat(fileName);
    final String url = baseUrl.concat(path);
    MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
    bodyMap.add("bitstreams", resource);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

    restTemplate.exchange(url, HttpMethod.POST, requestEntity, DSpaceBitStream.class);
  }
}
