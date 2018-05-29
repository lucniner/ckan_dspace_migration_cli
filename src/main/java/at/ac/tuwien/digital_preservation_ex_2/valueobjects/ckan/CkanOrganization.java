package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CkanOrganization {

  private String description;
  private String title;
  private String name;

  public String getDescription() {
    return description;
  }

  public CkanOrganization setDescription(final String description) {
    this.description = description;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public CkanOrganization setTitle(final String title) {
    this.title = title;
    return this;
  }

  public String getName() {
    return name;
  }

  public CkanOrganization setName(final String name) {
    this.name = name;
    return this;
  }
}
