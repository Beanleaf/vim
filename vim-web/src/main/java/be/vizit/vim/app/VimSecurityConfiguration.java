package be.vizit.vim.app;

import be.vizit.vim.domain.UserRole;
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
        .antMatchers("/").permitAll()
        .antMatchers("/admin/*").hasRole(UserRole.ADMIN.name())
        .antMatchers("/login*").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/db/**").denyAll()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/webjars/octicons/**").permitAll()
        .antMatchers("/webjars/jquery/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/", true)
        .failureUrl("/login_error")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(vimAuthenticationProvider);
  }
}
