package at.ac.tuwien.digital_preservation_ex_2;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.options.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLine {

  private static final int LINE_LENGTH = 50;
  private static final char FILLING = '*';
  private final Map<String, Option> options = new HashMap<>();


  public CommandLine(final RestTemplate restTemplate, final CkanConfigProperties properties, final DSpaceConfigProperties dSpaceConfigProperties) {
    final Option quit = new QuitOption("q", "quitting programm");
    final Option help = new HelpOption("h", "help command", options, System.out);
    final Option lsCkan = new CkanListingOption("ls-ckan", "listing ckan datasets", restTemplate, System.out, properties);
    final Option migrate = new MigrateOption("migrate", "migrating ...", restTemplate, System.out, properties, dSpaceConfigProperties);
    options.put(quit.getOptionCommand(), quit);
    options.put(lsCkan.getOptionCommand(), lsCkan);
    options.put(help.getOptionCommand(), help);
    options.put(migrate.getOptionCommand(), migrate);
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
}
