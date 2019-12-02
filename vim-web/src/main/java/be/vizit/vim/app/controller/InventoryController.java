package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.utils.FeedbackUtils;
import be.vizit.vim.app.utils.MessageType;
import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.ItemStatus;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.services.InventoryItemService;
import be.vizit.vim.services.InventoryLogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InventoryController extends VimController {

  private static final String URL_IN = "inventory/scan/in";
  private static final String VIEW_IN = "in";
  private static final String URL_OUT = "inventory/scan/out";
  private static final String VIEW_OUT = "out";

  private final InventoryLogService inventoryLogService;
  private final InventoryItemService inventoryItemService;

  @Autowired
  public InventoryController(VimSession vimSession,
      InventoryLogService inventoryLogService,
      InventoryItemService inventoryItemService) {
    super(vimSession);
    this.inventoryLogService = inventoryLogService;
    this.inventoryItemService = inventoryItemService;
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
  public String scanInPost(@RequestParam(value = "itemUuid") String itemUuid, Model model) {
    InventoryItem inventoryItem = inventoryItemService.findByUuidOrId(itemUuid.replace("#", ""));
    if (inventoryItem == null || !inventoryItem.isActive()) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.ERROR, "notifications.inventory.noActiveItemFound"
      ));
    } else if (inventoryItem.getCurrentStatus() == ItemStatus.AVAILABLE) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.WARNING, "notifications.inventory.itemAlreadyAvailable"
      ));
    } else {
      inventoryLogService.logIn(inventoryItem, getVimSession().getActiveUser());
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.SUCCESS, "notifications.inventory.successfullLogIn"
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
  public String scanOutPost(@RequestParam(value = "itemUuid") String itemUuid, Model model) {
    InventoryItem inventoryItem = inventoryItemService.findByUuidOrId(itemUuid.replace("#", ""));
    if (inventoryItem == null || !inventoryItem.isActive()) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.ERROR, "notifications.inventory.noActiveItemFound"
      ));
    } else if (inventoryItem.getCurrentStatus() == ItemStatus.LEND) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.WARNING, "notifications.inventory.itemAlreadyLend"
      ));
    } else {
      inventoryLogService.logOut(inventoryItem, getVimSession().getActiveUser());
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.SUCCESS, "notifications.inventory.successfullLogOut"
      ));
    }
    addRecentLogs(model, InventoryDirection.OUT);
    return VIEW_OUT;
  }
}
