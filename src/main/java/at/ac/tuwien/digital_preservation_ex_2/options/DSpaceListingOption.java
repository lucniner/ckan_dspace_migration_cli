package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.migration.DSpaceItemRetriever;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceItem;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DSpaceListingOption extends AbstractOption {

  private final RestTemplate restTemplate;
  private final OutputStream stream;
  private final DSpaceConfigProperties properties;

  public DSpaceListingOption(
      final String optionCommand,
      final String optionDescription,
      final RestTemplate restTemplate,
      final OutputStream stream,
      final DSpaceConfigProperties properties) {
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

    DSpaceItemRetriever dSpaceItemRetriever = new DSpaceItemRetriever(properties, restTemplate);
    printResult(dSpaceItemRetriever.getItems());
  }

  private void printResult(List<DSpaceItem> items) {
    try {
      stream.write(
          "The following PUBLIC data sets are available at the CKAN repository.\n".getBytes());
      for (DSpaceItem dSpaceItem : items) {
        stream.write(dSpaceItem.getName().getBytes());
        stream.write("\n".getBytes());
      }
      stream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
