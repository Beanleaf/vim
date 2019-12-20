package be.beanleaf.vim.app;

import be.beanleaf.vim.domain.entities.User;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class VimLocaleResolver extends SessionLocaleResolver {

  @Autowired
  private VimSession vimSession;

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    User activeUser = vimSession.getActiveUser();
    if (activeUser != null) {
      String languageTag = activeUser.getLanguageTag();
      if (!StringUtils.isEmpty(languageTag)) {
        return Locale.forLanguageTag(languageTag);
      }
    }
    return super.resolveLocale(request);
  }
}
