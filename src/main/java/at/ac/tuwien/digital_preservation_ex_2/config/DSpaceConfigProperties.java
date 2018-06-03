package at.ac.tuwien.digital_preservation_ex_2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dspace.repository")
public class DSpaceConfigProperties {

  private String protocol;
  private String host;
  private String port;


  public String getHost() {
    return host;
  }

  public DSpaceConfigProperties setHost(final String host) {
    this.host = host;
    return this;
  }

  public String getPort() {
    return port;
  }

  public DSpaceConfigProperties setPort(final String port) {
    this.port = port;
    return this;
  }

  public String getProtocol() {
    return protocol;
  }

  public DSpaceConfigProperties setProtocol(final String protocol) {
    this.protocol = protocol;
    return this;
  }
}
