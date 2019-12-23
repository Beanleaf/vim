package be.beanleaf.vim.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("vim.app")
public class VimProperties {

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
