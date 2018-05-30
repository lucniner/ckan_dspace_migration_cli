package at.ac.tuwien.digital_preservation_ex_2.options;

import at.ac.tuwien.digital_preservation_ex_2.config.DSpaceConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.migration.DSpaceSessionRetriever;
import at.ac.tuwien.digital_preservation_ex_2.migration.SessionHolder;
import at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan.DSpaceUser;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class LoginOption extends AbstractOption {

  private final RestTemplate restTemplate;
  private final DSpaceConfigProperties dSpaceConfigProperties;

  public LoginOption(
      String optionCommand,
      String optionDescription,
      RestTemplate restTemplate,
      DSpaceConfigProperties dSpaceConfigProperties) {
    super(optionCommand, optionDescription);
    this.restTemplate = restTemplate;
    this.dSpaceConfigProperties = dSpaceConfigProperties;
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
    DSpaceSessionRetriever dSpaceTokenRetriever =
        new DSpaceSessionRetriever(dSpaceConfigProperties, restTemplate);
    SessionHolder.setSession(dSpaceTokenRetriever.getSession(new DSpaceUser(email, password)));
    System.out.println("Successfully logged in.");
  }
}
