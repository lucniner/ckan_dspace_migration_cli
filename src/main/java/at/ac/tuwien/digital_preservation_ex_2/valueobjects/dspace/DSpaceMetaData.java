package at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace;

public class DSpaceMetaData {

  private String key;
  private String value;
  private String language = null;

  public DSpaceMetaData(final String key, final String value, final String language) {
    this.key = key;
    this.value = value;
    this.language = language;
  }

  public DSpaceMetaData() {}

  public String getKey() {
    return key;
  }

  public DSpaceMetaData setKey(final String key) {
    this.key = key;
    return this;
  }

  public String getValue() {
    return value;
  }

  public DSpaceMetaData setValue(final String value) {
    this.value = value;
    return this;
  }

  public String getLanguage() {
    return language;
  }

  public DSpaceMetaData setLanguage(final String language) {
    this.language = language;
    return this;
  }
}
