package at.ac.tuwien.digital_preservation_ex_2.valueobjects.dspace;

public class DSpaceCommunity {

  private String uuid;
  private String name;
  private String type;
  private String link;

  public DSpaceCommunity() {}

  public DSpaceCommunity(Integer uuid, String name, String type, String link) {
    this.name = name;
    this.type = type;
    this.link = link;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DSpaceCommunity{");
    sb.append("uuid=").append(uuid);
    sb.append(", name='").append(name).append('\'');
    sb.append(", type='").append(type).append('\'');
    sb.append(", link='").append(link).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
