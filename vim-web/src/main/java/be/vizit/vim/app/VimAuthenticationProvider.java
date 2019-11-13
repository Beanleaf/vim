package be.vizit.vim.app;

import be.vizit.vim.domain.entities.User;
import be.vizit.vim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class VimAuthenticationProvider implements AuthenticationProvider {

  private final VimSession vimSession;
  private final UserService userService;

  @Autowired
  public VimAuthenticationProvider(VimSession vimSession, UserService userService) {
    this.vimSession = vimSession;
    this.userService = userService;
  }

  private UsernamePasswordAuthenticationToken createUserAuthenticationToken(User user) {
    return new UsernamePasswordAuthenticationToken(user.getUsername(), null);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (vimSession.getActiveUser() != null) {
      return createUserAuthenticationToken(vimSession.getActiveUser());
    }

    String input = authentication.getName();
    User user = userService.login(input);
    if (user != null) {
      vimSession.setActiveUser(user);
      return createUserAuthenticationToken(vimSession.getActiveUser());
    }
    throw new BadCredentialsException("Authentication failed!");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}