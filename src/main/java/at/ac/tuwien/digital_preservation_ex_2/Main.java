package at.ac.tuwien.digital_preservation_ex_2;

import at.ac.tuwien.digital_preservation_ex_2.options.Option;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    final CommandLine cli = new CommandLine();

    System.out.println(cli.getWelcomeHeader());
    System.out.println("the following commands are at your disposal:");
    final Option help = cli.getHelpOption();
    help.executeOption();
    final Scanner scanner = new Scanner(System.in);
    String line = scanner.nextLine();
    while (line != null) {
      final Option o = cli.getOption(line);
      System.out.println(o.getOptionDescription());
      o.executeOption();
      line = scanner.nextLine();
    }
    System.exit(1);
  }
}
