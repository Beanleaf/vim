package be.beanleaf.vim.app;

import be.beanleaf.vim.app.controller.HomeController;
import be.beanleaf.vim.app.controller.SecurityController;
import be.beanleaf.vim.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class VimSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final VimAuthenticationProvider vimAuthenticationProvider;
  public static final String URL_LOGOUT = "/logout";
  private static final String URL_LOGGEDOUT = HomeController.URL_HOME + "?logout";

  @Autowired
  public VimSecurityConfiguration(VimAuthenticationProvider vimAuthenticationProvider) {
    this.vimAuthenticationProvider = vimAuthenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/admin/*").hasAuthority(UserRole.ADMIN.name())
          .antMatchers("/inventory/*").hasAnyAuthority(getAllPossibleUserRoles())
          .antMatchers("/db/**").denyAll()
          .antMatchers("/css/**").permitAll()
          .antMatchers("/js/**").permitAll()
          .antMatchers("/favicon.ico").permitAll()
          .antMatchers("/webjars/octicons/**").permitAll()
          .antMatchers("/webjars/jquery/**").permitAll()
          .antMatchers("/").permitAll()
        .and()
          .formLogin()
          .loginPage(SecurityController.URL_LOGIN)
          .failureUrl(SecurityController.URL_LOGIN_ERROR)
        .and()
          .logout()
          .logoutUrl(URL_LOGOUT)
          .logoutSuccessUrl(URL_LOGGEDOUT)
        .and()
          .sessionManagement()
          .invalidSessionUrl(URL_LOGGEDOUT)
          .maximumSessions(1)
          .expiredUrl(URL_LOGGEDOUT);
  }

  private String[] getAllPossibleUserRoles() {
    UserRole[] values = UserRole.values();
    int i = 0;
    String[] result = new String[values.length];
    for (UserRole value : values) {
      result[i++] = value.name();
    }
    return result;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(vimAuthenticationProvider);
  }
}
