package be.vizit.vim.app.controller;

import be.vizit.vim.app.ToastMessage;
import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.dto.InventoryItemDto;
import be.vizit.vim.domain.utilities.MessageType;
import be.vizit.vim.services.FeedbackService;
import be.vizit.vim.services.InventoryItemService;
import be.vizit.vim.services.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InventoryController extends VimController {

  private final InventoryItemService inventoryItemService;
  private final ItemCategoryService itemCategoryService;
  private final FeedbackService feedbackService;

  @Autowired
  public InventoryController(VimSession vimSession,
      InventoryItemService inventoryItemService, ItemCategoryService itemCategoryService,
      FeedbackService feedbackService) {
    super(vimSession);
    this.inventoryItemService = inventoryItemService;
    this.itemCategoryService = itemCategoryService;
    this.feedbackService = feedbackService;
  }

  @GetMapping({"/admin/inventory", "/admin/inventory/{page}"})
  public String inventory(@PathVariable(required = false) Integer page, Model model) {
    int startPage = page != null ? page : 0;
    model.addAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(startPage, 50)));
    model.addAttribute("itemCategories", itemCategoryService.findAllCategories());
    model.addAttribute("inventoryItemDto", new InventoryItemDto());
    return "admin/inventory";
  }

  @PostMapping("/admin/inventory")
  public ModelAndView newInventoryItem(@ModelAttribute InventoryItemDto inventoryItemDto,
      Model model) {
    try {
      inventoryItemService.createNewItem(
          inventoryItemDto.getItemCategory(),
          inventoryItemDto.getDescription(),
          inventoryItemDto.isActive(),
          getVimSession().getActiveUser()
      );
      model.addAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(0, 50)));
      model.addAttribute(
          new ToastMessage(MessageType.SUCCESS, "notifications.inventory.newItemSuccess", true));
    } catch (Exception e) {
      model.addAttribute(feedbackService.createMessage(e));
    }
    return render("/admin/inventory", model);
  }
}
