package at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace;

public class DSpaceMetadataField {

  private String name;
  private String element;
  private String qualifier;
  private String description;

  public DSpaceMetadataField() {}

  public DSpaceMetadataField(String name, String element, String qualifier, String description) {
    this.name = name;
    this.element = element;
    this.qualifier = qualifier;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getElement() {
    return element;
  }

  public void setElement(String element) {
    this.element = element;
  }

  public String getQualifier() {
    return qualifier;
  }

  public void setQualifier(String qualifier) {
    this.qualifier = qualifier;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DSpaceMetadataField{");
    sb.append("name='").append(name).append('\'');
    sb.append(", element='").append(element).append('\'');
    sb.append(", qualifier='").append(qualifier).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
