package at.ac.tuwien.digital_preservation_ex_2.options;

import org.springframework.stereotype.Component;

@Component
public class QuitOption extends AbstractOption {

  private static final String CMD = "q";
  private static final String DESCRIPTION = "Quitting program.";

  public QuitOption() {
    super(CMD, DESCRIPTION);
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
    System.exit(0);
  }
}
