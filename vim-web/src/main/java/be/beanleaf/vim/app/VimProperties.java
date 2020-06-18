package be.beanleaf.vim.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(VimProperties.PREFIX)
public class VimProperties {

  public static final String PREFIX = "vim.app";

  /**
   * Catch mail address for all outgoing mail
   */
  private String catchMailAddress;

  public String getCatchMailAddress() {
    return catchMailAddress;
  }

  public void setCatchMailAddress(String catchMailAddress) {
    this.catchMailAddress = catchMailAddress;
  }
}
