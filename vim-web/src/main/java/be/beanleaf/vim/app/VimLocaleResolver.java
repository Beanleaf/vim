package be.beanleaf.vim.app;

import be.beanleaf.vim.app.utils.LocaleUtils;
import be.beanleaf.vim.domain.entities.User;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class VimLocaleResolver extends SessionLocaleResolver implements WebMvcConfigurer {

  @Autowired
  private VimSession vimSession;

  @Override
  @SuppressWarnings("NullableProblems")
  public Locale resolveLocale(HttpServletRequest request) {
    User activeUser = vimSession.getActiveUser();
    if (activeUser != null) {
      Locale userLocale = LocaleUtils.getUserLocale(activeUser);
      return userLocale != null ? userLocale : super.resolveLocale(request);
    }
    return super.resolveLocale(request);
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
}
