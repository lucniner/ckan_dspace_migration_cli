package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HelpOption extends AbstractOption {

  private static final String CMD = "h";
  private static final String DESCRIPTION = "Help command";

  private final CommandLine commandLine;

  @Autowired
  public HelpOption(@Lazy final CommandLine commandLine) {
    super(CMD, DESCRIPTION);
    this.commandLine = commandLine;
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
    for (final Map.Entry<String, Option> optionEntry : commandLine.getOptions().entrySet()) {
      System.out.println(
          optionEntry.getValue().getOptionCommand()
              + "\t"
              + optionEntry.getValue().getOptionDescription());
    }
  }
}
