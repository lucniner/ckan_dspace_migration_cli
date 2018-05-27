package at.ac.tuwien.digital_preservation_ex_2.options;

public class QuitOption extends AbstractOption {
  public QuitOption(final String optionCommand, final String optionDescription) {
    super(optionCommand, optionDescription);
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
