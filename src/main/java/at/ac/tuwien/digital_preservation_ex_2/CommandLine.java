package at.ac.tuwien.digital_preservation_ex_2;

import at.ac.tuwien.digital_preservation_ex_2.options.HelpOption;
import at.ac.tuwien.digital_preservation_ex_2.options.Option;
import at.ac.tuwien.digital_preservation_ex_2.options.QuitOption;

import java.util.HashMap;
import java.util.Map;

public class CommandLine {

  private static final int LINE_LENGTH = 50;
  private static final char FILLING = '*';
  private final Map<String, Option> options = new HashMap<>();

  public CommandLine() {
    init();
  }

  private void init() {
    final Option quit = new QuitOption("q", "quitting programm");
    final Option help = new HelpOption("h", "help command", options, System.out);
    options.put(quit.getOptionCommand(), quit);
    options.put(help.getOptionCommand(), help);
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
