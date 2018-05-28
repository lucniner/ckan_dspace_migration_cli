package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.CkanPackageResult;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class CkanListingOption extends AbstractOption {

  private final RestTemplate restTemplate;
  private final OutputStream stream;
  private final CkanConfigProperties properties;

  public CkanListingOption(
          final String optionCommand,
          final String optionDescription,
          final RestTemplate restTemplate,
          final OutputStream stream,
          final CkanConfigProperties properties) {
    super(optionCommand, optionDescription);
    this.restTemplate = restTemplate;
    this.stream = stream;
    this.properties = properties;
  }

  @Override
  public String getOptionCommand() {
    return optionCommand;
  }

  @Override
  public String getOptionDescription() {
    return optionDescription;
  }

  @Override
  public void executeOption() {

    final String baseUrl =
            properties
                    .getProtocol()
                    .concat(properties.getHost())
                    .concat(":")
                    .concat(properties.getPort());
    final String path = "/api/3/action/package_list";
    final String url = baseUrl.concat(path);
    final CkanPackageResult response = restTemplate.getForObject(url, CkanPackageResult.class);
    printResult(response);
  }

  private void printResult(CkanPackageResult result) {
    try {
      stream.write("The following PUBLIC data sets are available at the CKAN repository.\n".getBytes());
      final String[] results = result.getResult();
      for (int i = 0; i < results.length; i++) {
        stream.write(results[i].getBytes());
        stream.write("\n".getBytes());
      }
      stream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
