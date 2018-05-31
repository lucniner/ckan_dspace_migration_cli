package at.ac.tuwien.digital_preservation_ex_2;

import at.ac.tuwien.digital_preservation_ex_2.options.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLine {

  private static final int LINE_LENGTH = 50;
  private static final char FILLING = '*';
  private final Map<String, Option> options;

  @Autowired
  public CommandLine(
      final CkanListingOption ckanListingOption,
      final DSpaceListingOption dSpaceListingOption,
      final HelpOption helpOption,
      final LoginOption loginOption,
      final MigrateOption migrateOption,
      final QuitOption quitOption) {
    options = new HashMap<>();
    options.put(quitOption.getOptionCommand(), quitOption);
    options.put(ckanListingOption.getOptionCommand(), ckanListingOption);
    options.put(dSpaceListingOption.getOptionCommand(), dSpaceListingOption);
    options.put(helpOption.getOptionCommand(), helpOption);
    options.put(migrateOption.getOptionCommand(), migrateOption);
    options.put(loginOption.getOptionCommand(), loginOption);
  }

  public Option getOption(final String argument) {
    return options.get(argument);
  }

  public Option getHelpOption() {
    for (final Map.Entry<String, Option> optionEntry : options.entrySet()) {
      if (optionEntry.getValue() instanceof HelpOption) {
        return optionEntry.getValue();
      }
    }
    return null;
  }

  public String getWelcomeHeader() {
    String line = getLineFilling(FILLING, LINE_LENGTH) + "\n";
    line += "*\t" + getLineFilling(' ', LINE_LENGTH - 5) + "*\n";
    line += "*\tWelcome to the CKAN-DSPACE migration tool";
    line += getLineFilling(' ', 4) + "*\n";
    line += "*\t" + getLineFilling(' ', LINE_LENGTH - 5) + "*\n";
    line += getLineFilling(FILLING, LINE_LENGTH);
    return line;
  }

  private String getLineFilling(final char representation, final int lineLength) {
    String line = "";
    for (int i = 0; i < lineLength; i++) {
      line += representation;
    }
    return line;
  }

  public Map<String, Option> getOptions() {
    return options;
  }
}
