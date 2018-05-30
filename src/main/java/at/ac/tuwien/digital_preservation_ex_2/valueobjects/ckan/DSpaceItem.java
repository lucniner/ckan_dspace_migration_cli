package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

import java.util.ArrayList;
import java.util.List;

public class DSpaceItem {

  private String uuid;
  private List<DSpaceMetaData> metadata = new ArrayList<>();
  private String name;

  public DSpaceItem() {
  }

  public DSpaceItem(final String name) {
    this.name = name;
  }

  public String getUuid() {
    return uuid;
  }

  public DSpaceItem setUuid(final String uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getName() {
    return name;
  }

  public DSpaceItem setName(final String name) {
    this.name = name;
    return this;
  }

  public void addMetadata(final String key, final String value) {
    this.metadata.add(new DSpaceMetaData(key, value, null));
  }


  public List<DSpaceMetaData> getMetadata() {
    return metadata;
  }

}
