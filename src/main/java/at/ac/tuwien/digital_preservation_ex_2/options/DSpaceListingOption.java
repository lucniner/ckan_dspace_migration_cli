package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.migration.dspace.DSpaceItemRetriever;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DSpaceListingOption extends AbstractOption {

  private static final String CMD = "ls-dspace";
  private static final String DESCRIPTION = "Listing dspace datasets";

  private final DSpaceItemRetriever dSpaceItemRetriever;

  @Autowired
  public DSpaceListingOption(final DSpaceItemRetriever dSpaceItemRetriever) {
    super(CMD, DESCRIPTION);
    this.dSpaceItemRetriever = dSpaceItemRetriever;
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
    printResult(dSpaceItemRetriever.getItems());
  }

  private void printResult(List<DSpaceItem> items) {
    System.out.println("The following PUBLIC data sets are available at the CKAN repository.");
    for (DSpaceItem dSpaceItem : items) {
      System.out.println(dSpaceItem.getName());
    }
  }
}
