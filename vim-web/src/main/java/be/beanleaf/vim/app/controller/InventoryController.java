package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.DefectDto;
import be.beanleaf.vim.app.services.VimMailService;
import be.beanleaf.vim.app.utils.FeedbackUtils;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.UserRole;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.services.InventoryItemService;
import be.beanleaf.vim.services.InventoryLogService;
import be.beanleaf.vim.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InventoryController extends VimController {

  public static final String URL_DEFECT = "/inventory/defect";
  private static final String VIEW_IN = "in";
  private static final String URL_IN = "/inventory/scan/in";
  private static final String VIEW_OUT = "out";
  private static final String URL_OUT = "/inventory/scan/out";
  private static final String VIEW_DEFECT = "admin/inventory/defectForm";

  private final InventoryLogService inventoryLogService;
  private final InventoryItemService inventoryItemService;
  private final VimMailService vimMailService;
  private final UserService userService;

  @Autowired
  public InventoryController(VimSession vimSession,
      InventoryLogService inventoryLogService,
      InventoryItemService inventoryItemService,
      VimMailService vimMailService, UserService userService) {
    super(vimSession);
    this.inventoryLogService = inventoryLogService;
    this.inventoryItemService = inventoryItemService;
    this.vimMailService = vimMailService;
    this.userService = userService;
  }

  private void addRecentLogs(Model model, InventoryDirection direction) {
    List<InventoryLog> recentLogsForUser = inventoryLogService
        .findRecentLogsForUser(getVimSession().getActiveUser(), direction);
    model.addAttribute("recentLogs", recentLogsForUser);
  }

  @GetMapping(URL_IN)
  public String scanIn(Model model) {
    addRecentLogs(model, InventoryDirection.IN);
    return VIEW_IN;
  }

  @PostMapping(URL_IN)
  public String scanInPost(
      @RequestParam(value = "itemUuid") String itemUuid,
      Model model) {
    InventoryItem inventoryItem = inventoryItemService.findByUuidOrId(itemUuid.replace("#", ""));
    if (inventoryItem == null || !inventoryItem.isActive()) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.ERROR, "notifications.inventory.noActiveItemFound", true
      ));
    } else if (inventoryItem.getCurrentStatus() == ItemStatus.AVAILABLE) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.WARNING, "notifications.inventory.itemAlreadyAvailable", true
      ));
    } else {
      inventoryLogService.logIn(inventoryItem, getVimSession().getActiveUser());
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.SUCCESS, "notifications.inventory.successfullLogIn", true
      ));
    }
    addRecentLogs(model, InventoryDirection.IN);
    return VIEW_IN;
  }

  @GetMapping(URL_OUT)
  public String scanOut(Model model) {
    addRecentLogs(model, InventoryDirection.OUT);
    return VIEW_OUT;
  }

  @PostMapping(URL_OUT)
  public String scanOutPost(
      @RequestParam(value = "itemUuid") String itemUuid,
      Model model) {
    InventoryItem inventoryItem = inventoryItemService.findByUuidOrId(itemUuid.replace("#", ""));
    if (inventoryItem == null || !inventoryItem.isActive()) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.ERROR, "notifications.inventory.noActiveItemFound", true
      ));
    } else if (inventoryItem.getCurrentStatus() == ItemStatus.LEND) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.WARNING, "notifications.inventory.itemAlreadyLend", true
      ));
    } else {
      inventoryLogService.logOut(inventoryItem, getVimSession().getActiveUser());
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.SUCCESS, "notifications.inventory.successfullLogOut", true
      ));
    }
    addRecentLogs(model, InventoryDirection.OUT);
    return VIEW_OUT;
  }

  @GetMapping(URL_DEFECT + "/{id}")
  public String getDefectForm(
      @PathVariable long id,
      Model model) {
    model.addAttribute("inventoryLog", inventoryLogService.getInventoryLog(id));
    model.addAttribute("defectDto", new DefectDto());
    model.addAttribute("frmAction", URL_DEFECT + "/" + id);
    return VIEW_DEFECT;
  }

  @PostMapping(URL_DEFECT + "/{id}")
  public String defectForm(
      @ModelAttribute DefectDto dto,
      @PathVariable long id,
      RedirectAttributes redirectAttributes) {
    InventoryLog log = inventoryLogService.getInventoryLog(id);
    inventoryLogService.logDefect(log, dto.getComment());
    try {
      Map<String, Object> variables = new HashMap<>();
      variables.put("defectItem", log.getInventoryItem());
      variables.put("comment", log.getComment());
      variables.put("reporter", getVimSession().getActiveUser().getName());
      variables.put("time", log.getTimestamp());
      List<User> administrators = userService.findActiveUsersByRole(UserRole.ADMIN);
      if (CollectionUtils.isEmpty(administrators)) {
        throw new IllegalStateException("There should be at least one administrator");
      }
      for (User administrator : administrators) {
        variables.put("recipientName", administrator.getName());
        vimMailService.sendMail("mails/defectItem", getLocaleString("mail.defect.subject"),
            administrator.getEmailAddress(), variables, administrator.getLanguageTag());
      }
      redirectAttributes.addFlashAttribute(
          new ToastMessage(MessageType.SUCCESS, "notifications.inventory.defectSuccess", true));
    } catch (MessagingException e) {
      redirectAttributes.addFlashAttribute(FeedbackUtils.createMessage(e));
    }
    return redirect(URL_IN);
  }
}
