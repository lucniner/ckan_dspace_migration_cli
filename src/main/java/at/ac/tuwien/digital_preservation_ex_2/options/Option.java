package at.ac.tuwien.digital_preservation_ex_2.options;

public interface Option {

  String getOptionCommand();

  String getOptionDescription();

  void executeOption();
}
