package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

import java.util.Arrays;

public class SimpleCkanResult {

  private String help;
  private boolean success;
  private String[] result;

  public SimpleCkanResult() {
  }

  public String getHelp() {
    return help;
  }

  public SimpleCkanResult setHelp(final String help) {
    this.help = help;
    return this;
  }

  public boolean isSuccess() {
    return success;
  }

  public SimpleCkanResult setSuccess(final boolean success) {
    this.success = success;
    return this;
  }

  public String[] getResult() {
    return result;
  }

  public SimpleCkanResult setResult(final String[] result) {
    this.result = result;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SimpleCkanResult{");
    sb.append("help='").append(help).append('\'');
    sb.append(", success=").append(success);
    sb.append(", result=").append(Arrays.toString(result));
    sb.append('}');
    return sb.toString();
  }
}
