package at.ac.tuwien.digital_preservation_ex_2.options;

public abstract class AbstractOption implements Option {

  protected final String optionCommand;
  protected final String optionDescription;

  protected AbstractOption(final String optionCommand, final String optionDescription) {
    this.optionCommand = optionCommand;
    this.optionDescription = optionDescription;
  }
}
