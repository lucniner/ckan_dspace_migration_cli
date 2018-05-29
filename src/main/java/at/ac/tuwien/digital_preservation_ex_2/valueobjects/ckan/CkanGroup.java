package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CkanGroup {

  private String display_name;
  private String description;
  private String name;

  public String getDisplay_name() {
    return display_name;
  }

  public CkanGroup setDisplay_name(final String display_name) {
    this.display_name = display_name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public CkanGroup setDescription(final String description) {
    this.description = description;
    return this;
  }

  public String getName() {
    return name;
  }

  public CkanGroup setName(final String name) {
    this.name = name;
    return this;
  }
}
