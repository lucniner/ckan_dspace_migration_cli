package at.ac.tuwien.digital_preservation_ex_2;

import at.ac.tuwien.digital_preservation_ex_2.config.CkanConfigProperties;
import at.ac.tuwien.digital_preservation_ex_2.options.Option;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CliRunner implements CommandLineRunner {

  private final CommandLine cli;
  private final CkanConfigProperties ckanConfigProperties;

  public CliRunner(final CommandLine cli, final CkanConfigProperties ckanConfigProperties) {
    this.cli = cli;
    this.ckanConfigProperties = ckanConfigProperties;
  }

  @Override
  public void run(final String... args) throws Exception {

    System.out.println(cli.getWelcomeHeader());
    System.out.println("the following commands are at your disposal:");
    final Option help = cli.getHelpOption();
    help.executeOption();
//    final Scanner scanner = new Scanner(System.in);
//    String line = scanner.nextLine();

//    while (line != null) {
    final Option o = cli.getOption("migrate");
      System.out.println(o.getOptionDescription());
      o.executeOption();
//      line = scanner.nextLine();
//    }
    System.exit(1);
  }
}
