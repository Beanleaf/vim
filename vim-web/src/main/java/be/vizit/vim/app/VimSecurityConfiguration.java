package be.vizit.vim.app;

import be.vizit.vim.app.controller.HomeController;
import be.vizit.vim.app.controller.SecurityController;
import be.vizit.vim.domain.UserRole;
import javax.annotation.PreDestroy;
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

  @Autowired
  public VimSecurityConfiguration(VimAuthenticationProvider vimAuthenticationProvider) {
    this.vimAuthenticationProvider = vimAuthenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
          .antMatchers("/login*").permitAll()
          .antMatchers("/admin/*").hasAuthority(UserRole.ADMIN.name())
          .antMatchers("/db/**").denyAll()
          .antMatchers("/css/**").permitAll()
          .antMatchers("/js/**").permitAll()
          .antMatchers("/favicon.ico").permitAll()
          .antMatchers("/webjars/octicons/**").permitAll()
          .antMatchers("/webjars/jquery/**").permitAll()
          .antMatchers("/").permitAll()
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage(SecurityController.URL_LOGIN)
          .defaultSuccessUrl(HomeController.URL_HOME, true)
          .failureUrl(SecurityController.URL_LOGIN_ERROR)
          .and()
        .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl(HomeController.URL_HOME);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(vimAuthenticationProvider);
  }

  @PreDestroy
  private void destroySecurityContext() {
    //SecurityContextHolde
  }
}
