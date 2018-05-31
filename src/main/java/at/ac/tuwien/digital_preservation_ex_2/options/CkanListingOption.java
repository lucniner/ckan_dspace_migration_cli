package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.migration.ckan.CkanPackageRetriever;
import org.springframework.stereotype.Component;

@Component
public class CkanListingOption extends AbstractOption {

  private static final String CMD = "ls-ckan";
  private static final String DESCRIPTION = "Listing ckan datasets";

  private final CkanPackageRetriever ckanPackageRetriever;

  public CkanListingOption(final CkanPackageRetriever ckanPackageRetriever) {
    super(CMD, DESCRIPTION);
    this.ckanPackageRetriever = ckanPackageRetriever;
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
    printResult(ckanPackageRetriever.getPackageList());
  }

  private void printResult(String[] results) {
    System.out.println("The following PUBLIC data sets are available at the CKAN repository.");
    for (int i = 0; i < results.length; i++) {
      System.out.println(results[i]);
    }
  }
}
