package at.ac.tuwien.digital_preservation_ex_2.migration.dspace;

import org.springframework.stereotype.Component;

@Component
public class DSpaceSessionHolder {

  private String session;

  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
  }
}
