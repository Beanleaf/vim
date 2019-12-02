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

  @GetMapping(URL_IN)
  public String scanIn(Model model) {
    List<InventoryLog> recentLogsForUser = inventoryLogService
        .findRecentLogsForUser(getVimSession().getActiveUser(), InventoryDirection.IN);
    model.addAttribute("recentLogs", recentLogsForUser);
    return VIEW_IN;
  }

  @PostMapping(URL_IN)
  public String scanInPost(@RequestParam(value = "itemUuid") String itemUuid, Model model) {
    InventoryItem inventoryItem = inventoryItemService.findByUuidOrId(itemUuid.replace("#", ""));
    if (inventoryItem == null || !inventoryItem.isActive()) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.WARNING, "notifications.inventory.noActiveItemFound"
      ));
      return VIEW_IN;
    }
    model.addAttribute("loggedItem", inventoryItem);
    if (inventoryItem.getCurrentStatus() == ItemStatus.AVAILABLE) {
      model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
          MessageType.WARNING, "notifications.inventory.itemAlreadyAvailable"
      ));
      return VIEW_IN;
    }
    inventoryLogService.logIn(inventoryItem, getVimSession().getActiveUser());
    model.addAttribute("frmFeedback", FeedbackUtils.createMessage(
        MessageType.SUCCESS, "notifications.inventory.successfullLogIn"
    ));
    return VIEW_IN;
  }
}
