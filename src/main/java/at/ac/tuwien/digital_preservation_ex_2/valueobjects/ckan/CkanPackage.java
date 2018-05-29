package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CkanPackage {

  private String maintainer;
  private String maintainer_email;

  private String author_email;
  private String author;

  private String name;
  private String title;
  private String url;
  private String notes;

  private String license_id;
  private String license_title;

  private CkanResources[] resources;
  private CkanGroups[] groups;
  private CkanOrganization organization;

  public String getMaintainer() {
    return maintainer;
  }

  public CkanPackage setMaintainer(final String maintainer) {
    this.maintainer = maintainer;
    return this;
  }

  public String getMaintainer_email() {
    return maintainer_email;
  }

  public CkanPackage setMaintainer_email(final String maintainer_email) {
    this.maintainer_email = maintainer_email;
    return this;
  }

  public String getAuthor_email() {
    return author_email;
  }

  public CkanPackage setAuthor_email(final String author_email) {
    this.author_email = author_email;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public CkanPackage setAuthor(final String author) {
    this.author = author;
    return this;
  }

  public String getName() {
    return name;
  }

  public CkanPackage setName(final String name) {
    this.name = name;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public CkanPackage setTitle(final String title) {
    this.title = title;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public CkanPackage setUrl(final String url) {
    this.url = url;
    return this;
  }

  public String getNotes() {
    return notes;
  }

  public CkanPackage setNotes(final String notes) {
    this.notes = notes;
    return this;
  }

  public String getLicense_id() {
    return license_id;
  }

  public CkanPackage setLicense_id(final String license_id) {
    this.license_id = license_id;
    return this;
  }

  public String getLicense_title() {
    return license_title;
  }

  public CkanPackage setLicense_title(final String license_title) {
    this.license_title = license_title;
    return this;
  }

  public CkanResources[] getResources() {
    return resources;
  }

  public CkanPackage setResources(final CkanResources[] resources) {
    this.resources = resources;
    return this;
  }

  public CkanGroups[] getGroups() {
    return groups;
  }

  public CkanPackage setGroups(final CkanGroups[] groups) {
    this.groups = groups;
    return this;
  }

  public CkanOrganization getOrganization() {
    return organization;
  }

  public CkanPackage setOrganization(final CkanOrganization organization) {
    this.organization = organization;
    return this;
  }
}
