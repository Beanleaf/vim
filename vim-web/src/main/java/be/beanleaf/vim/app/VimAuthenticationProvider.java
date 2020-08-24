package be.beanleaf.vim.app;

import be.beanleaf.vim.app.utils.WebUtils;
import be.beanleaf.vim.domain.UserRole;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.services.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    UserRole userRole = user.getUserRole();
    List<UserRole> allInheritedRoles = WebUtils.getAllInheritedRoles(userRole);
    List<SimpleGrantedAuthority> authorities = allInheritedRoles.stream().map(
        (role) -> new SimpleGrantedAuthority(role.name())
    ).collect(Collectors.toList());
    return new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
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
      return createUserAuthenticationToken(user);
    }
    throw new BadCredentialsException("Authentication failed!");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
