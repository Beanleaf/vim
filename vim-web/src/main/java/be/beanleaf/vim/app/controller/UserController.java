package be.beanleaf.vim.app.controller;

import be.beanleaf.datatable.DataTable;
import be.beanleaf.datatable.DataTableColumn;
import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.UserDto;
import be.beanleaf.vim.app.services.VimMailService;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController extends VimController {

  private static final String URL_OVERVIEW = "/admin/users";
  private static final String VIEW_OVERVIEW = "admin/users/overview";
  private static final String URL_NEW_USER = "/admin/users/new";
  private static final String VIEW_EDIT_USER = "admin/users/editUser";
  private static final String URL_DELETE_USER = "/admin/users/delete";
  private static final String URL_EDIT_USER = "/admin/users/edit";
  private static final String URL_INACTIVATE_USER = "/admin/users/inactivate";

  private final UserService userService;
  private final VimMailService vimMailService;

  @Autowired
  public UserController(VimSession vimSession, UserService userService,
      VimMailService vimMailService) {
    super(vimSession);
    this.userService = userService;
    this.vimMailService = vimMailService;
  }

  @GetMapping(URL_OVERVIEW)
  public String users(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) boolean inactive, @RequestParam(required = false) String q,
      Model model) {
    DataTable<User> table = new DataTable<User>(page, 15) {
      @Override
      public long getCount() {
        return userService.countUsers(q, !inactive, null);
      }

      @Override
      public List<User> getData() {
        PageRequest page = PageRequest
            .of(getCurrentPage(), getPageSize(), Sort.by("firstName", "lastName"));
        return userService.findUsers(q, !inactive, null, page);
      }

      @Override
      public List<DataTableColumn<User>> getColumns() {
        return null;
      }
    };
    model.addAttribute("dataTable", table);
    model.addAttribute("nameFilter", q);
    model.addAttribute("inactiveShown", inactive);
    model.addAttribute("amountActive", userService.countUsers(q, true, null));
    model.addAttribute("amountInactive", userService.countUsers(q, false, null));
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_NEW_USER)
  public String newUser(Model model) {
    model.addAttribute(new UserDto());
    return VIEW_EDIT_USER;
  }

  @PostMapping(URL_NEW_USER)
  public String newUserPost(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult,
      Model model, RedirectAttributes redirectAttributes) throws MessagingException {
    model.addAttribute(userDto);
    if (bindingResult.hasErrors()) {
      return VIEW_EDIT_USER;
    }
    User user = userService.createNewUser(
        userDto.isActive(), userDto.getEmail(), userDto.getFirstName(), userDto.getLastName(),
        userDto.getPhonenumber(), userDto.getUserRole(), userDto.getUsername()
    );
    userService.save(user);
    if (userDto.isNotifyMail()) {
      Map<String, Object> mailVariables = new HashMap<>();
      mailVariables.put("newUser", user);
      vimMailService.sendMail("mails/welcomeMail", getLocaleString("mail.welcome.subject"),
          user.getEmailAddress(), mailVariables);
    }
    redirectAttributes.addFlashAttribute(new ToastMessage(MessageType.SUCCESS,
        "notifications.users.newUserSuccess", true));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_DELETE_USER + "/{id}")
  public String deleteUser(@PathVariable long id, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(id);
    userService.delete(user);
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.user.deleteSuccess"));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_INACTIVATE_USER + "/{id}")
  public String inactivateUser(@PathVariable long id, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(id);
    userService.deactivateUser(user);
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.user.inactivateSuccess"));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_EDIT_USER + "/{id}")
  public String editUser(@PathVariable long id, Model model) {
    User user = userService.getUser(id);
    model.addAttribute("isDeletable", userService.isDeletable(user));
    model.addAttribute("originalUser", user);
    model.addAttribute(new UserDto(
        user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmailAddress(),
        user.getPhonenumber(), user.getUserRole(), user.isActive(), false
    ));
    return VIEW_EDIT_USER;
  }

  @PostMapping(URL_EDIT_USER + "/{id}")
  public String editUserPost(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult,
      @PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
    User user = userService.getUser(id);
    model.addAttribute("originalUser", user);
    model.addAttribute(userDto);
    User userByMail = userService.findUserByEmailAddress(userDto.getEmail());
    if (userByMail != null && userByMail != user) {
      bindingResult.rejectValue("email", "users.emailInUse");
    }
    User userByUsername = userService.getUserByUsername(userDto.getUsername());
    if (userByUsername != null && userByUsername != user) {
      bindingResult.rejectValue("username", "users.usernameInUse");
    }
    if (!bindingResult.hasErrors()) {
      userService.updateUser(user, userDto.getEmail(), userDto.isActive(), userDto.getFirstName(),
          userDto.getLastName(), userDto.getPhonenumber(), userDto.getUserRole(),
          userDto.getUsername());
      redirectAttributes.addFlashAttribute(new ToastMessage(MessageType.SUCCESS,
          "notifications.users.editSuccess", true));
      return redirect(URL_OVERVIEW);
    }
    return VIEW_EDIT_USER;
  }

  @GetMapping(value = "admin/user/usernameCheck")
  public @ResponseBody
  boolean ajaxCheckUsernameAvailability(@RequestParam("toCheck") String username,
      @RequestParam(value = "id", required = false) Long id) {
    User originalUser = id != null ? userService.getUser(id) : null;
    User userByUsername = userService.getUserByUsername(username);
    return originalUser == userByUsername || userByUsername == null;
  }

  @GetMapping(value = "admin/user/emailCheck")
  public @ResponseBody
  boolean ajaxCheckEmailAvailability(@RequestParam("toCheck") String email,
      @RequestParam(value = "id", required = false) Long id) {
    User originalUser = id != null ? userService.getUser(id) : null;
    User userByEmailAddress = userService.findUserByEmailAddress(email);
    return originalUser == userByEmailAddress || userByEmailAddress == null;
  }

}
