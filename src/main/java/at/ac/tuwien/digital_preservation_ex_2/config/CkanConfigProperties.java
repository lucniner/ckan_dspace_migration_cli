package at.ac.tuwien.digital_preservation_ex_2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ckan.repository")
public class CkanConfigProperties {

  private String protocol;
  private String host;
  private String port;


  public String getHost() {
    return host;
  }

  public CkanConfigProperties setHost(final String host) {
    this.host = host;
    return this;
  }

  public String getPort() {
    return port;
  }

  public CkanConfigProperties setPort(final String port) {
    this.port = port;
    return this;
  }

  public String getProtocol() {
    return protocol;
  }

  public CkanConfigProperties setProtocol(final String protocol) {
    this.protocol = protocol;
    return this;
  }
}
