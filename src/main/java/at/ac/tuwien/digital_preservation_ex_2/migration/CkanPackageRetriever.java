package at.ac.tuwien.digital_preservation_ex_2.migration;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.CkanPackage;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.SimpleCkanResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CkanPackageRetriever {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final CkanConfigProperties properties;
  private final RestTemplate restTemplate;

  private final String baseUrl;
  private final String packageListUrl;

  public CkanPackageRetriever(
          final CkanConfigProperties properties, final RestTemplate restTemplate) {
    this.properties = properties;
    this.restTemplate = restTemplate;

    this.baseUrl =
            properties
                    .getProtocol()
                    .concat(properties.getHost())
                    .concat(":")
                    .concat(properties.getPort());
    packageListUrl = baseUrl.concat("/api/3/action/package_list");
  }

  public List<CkanPackage> getPackages() {
    final List<CkanPackage> ckanPackages = new ArrayList<>();
    final String[] packages = getPackageList();
    for (final String packageName : packages) {
      getPackage(packageName).ifPresent(ckanPackages::add);
    }
    return ckanPackages;
  }

  private String[] getPackageList() {
    final SimpleCkanResult result =
            restTemplate.getForObject(packageListUrl, SimpleCkanResult.class);
    return result.getResult();
  }

  private Optional<CkanPackage> getPackage(final String name) {
    final String url = baseUrl.concat("/api/3/action/package_show?id=").concat(name);
    final String result = restTemplate.getForObject(url, String.class);
    return extractPackage(result);
  }

  private Optional<CkanPackage> extractPackage(final String result) {
    try {
      final JsonNode node = objectMapper.readTree(result);
      JsonNode result2 = node.path("result");
      return Optional.of(objectMapper.convertValue(result2, CkanPackage.class));
    } catch (IOException e) {
      return Optional.empty();
    }
  }
}
