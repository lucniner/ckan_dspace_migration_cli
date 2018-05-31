package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.migration.dspace.DSpaceSessionHolder;
import at.ac.tuwien.digital_preservation_ex_2.migration.dspace.DSpaceSessionRetriever;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace.DSpaceUser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LoginOption extends AbstractOption {

  private static final String CMD = "login";
  private static final String DESCRIPTION = "Login to dspace";

  private final DSpaceSessionRetriever dSpaceSessionRetriever;
  private final DSpaceSessionHolder DSpaceSessionHolder;

  public LoginOption(
      final DSpaceSessionRetriever dSpaceSessionRetriever,
      final DSpaceSessionHolder DSpaceSessionHolder) {
    super(CMD, DESCRIPTION);
    this.dSpaceSessionRetriever = dSpaceSessionRetriever;
    this.DSpaceSessionHolder = DSpaceSessionHolder;
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
    final Scanner scanner = new Scanner(System.in);
    System.out.println("E-Mail:");
    String email = scanner.nextLine();
    System.out.println("Password:");
    String password = scanner.nextLine();
    DSpaceSessionHolder.setSession(
        dSpaceSessionRetriever.getSession(new DSpaceUser(email, password)));
    System.out.println("Successfully logged in.");
  }
}
