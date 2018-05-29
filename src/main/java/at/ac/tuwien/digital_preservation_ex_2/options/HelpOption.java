package at.ac.tuwien.digital_preservation_ex_2.options;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HelpOption extends AbstractOption {

  final Map<String, Option> options;
  final OutputStream stream;

  public HelpOption(
      final String optionCommand,
      final String optionDescription,
      final Map<String, Option> options,
      final OutputStream stream) {
    super(optionCommand, optionDescription);
    this.options = options;
    this.stream = stream;
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
    try {
      for (final Map.Entry<String, Option> optionEntry : options.entrySet()) {
        stream.write(optionEntry.getValue().getOptionCommand().getBytes());
        stream.write("\t\t\t".getBytes());
        stream.write(optionEntry.getValue().getOptionDescription().getBytes());
        stream.write("\n".getBytes());
      }
      stream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
