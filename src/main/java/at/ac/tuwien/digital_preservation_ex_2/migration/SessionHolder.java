package at.ac.tuwien.digital_preservation_ex_2.migration;

public class SessionHolder {

  private static String session;

  public static void setSession(String session) {
    SessionHolder.session = session;
  }

  public static String getSession() {
    return session;
  }
}
