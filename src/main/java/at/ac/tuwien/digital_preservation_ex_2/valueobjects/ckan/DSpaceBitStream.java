package at.ac.tuwien.digital_preservation_ex_2.valueobjects.ckan;

public class DSpaceBitStream {

  private long id;
  private String mimeType;

  public String getMimeType() {
    return mimeType;
  }

  public DSpaceBitStream setMimeType(final String mimeType) {
    this.mimeType = mimeType;
    return this;
  }

  public long getId() {
    return id;
  }

  public DSpaceBitStream setId(final long id) {
    this.id = id;
    return this;
  }
}
