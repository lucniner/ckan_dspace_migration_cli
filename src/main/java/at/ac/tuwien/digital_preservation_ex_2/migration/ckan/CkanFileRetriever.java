package at.ac.tuwien.digital_preservation_ex_2.migration.ckan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CkanFileRetriever {

  private final RestTemplate restTemplate;

  @Autowired
  public CkanFileRetriever(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public byte[] getFile(String url) {
    return restTemplate.getForObject(url, byte[].class);
  }
}
