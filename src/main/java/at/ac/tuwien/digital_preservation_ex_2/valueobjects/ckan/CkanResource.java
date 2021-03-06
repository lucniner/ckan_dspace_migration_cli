package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CkanResource {

  private String mimetype;
  private String description;
  private String name;
  private String format;
  private String url;
  private String created;
  private long size;

  public String getMimetype() {
    return mimetype;
  }

  public CkanResource setMimetype(final String mimetype) {
    this.mimetype = mimetype;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public CkanResource setDescription(final String description) {
    this.description = description;
    return this;
  }

  public String getName() {
    return name;
  }

  public CkanResource setName(final String name) {
    this.name = name;
    return this;
  }

  public String getFormat() {
    return format;
  }

  public CkanResource setFormat(final String format) {
    this.format = format;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public CkanResource setUrl(final String url) {
    this.url = url;
    return this;
  }

  public String getCreated() {
    return created;
  }

  public CkanResource setCreated(final String created) {
    this.created = created;
    return this;
  }

  public long getSize() {
    return size;
  }

  public CkanResource setSize(final long size) {
    this.size = size;
    return this;
  }
}
