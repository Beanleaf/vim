package be.vizit.vim.app.controller;

import be.vizit.vim.app.ToastMessage;
import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.dto.InventoryItemDto;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.utilities.MessageType;
import be.vizit.vim.services.InventoryItemService;
import be.vizit.vim.services.ItemCategoryService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InventoryController extends VimController {

  private static final String VIEW_NEW_ITEM = "admin/inventory/newInventoryItem";
  private static final String VIEW_OVERVIEW = "admin/inventory/overview";

  private final InventoryItemService inventoryItemService;
  private final ItemCategoryService itemCategoryService;

  @Autowired
  public InventoryController(VimSession vimSession,
      InventoryItemService inventoryItemService, ItemCategoryService itemCategoryService) {
    super(vimSession);
    this.inventoryItemService = inventoryItemService;
    this.itemCategoryService = itemCategoryService;
  }

  @GetMapping({"admin/inventory", "admin/inventory/{page}"})
  public String inventory(@PathVariable(required = false) Integer page, Model model) {
    int startPage = page != null ? page : 0;
    model.addAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(startPage, 50)));
    return VIEW_OVERVIEW;
  }

  @GetMapping("admin/inventory/delete/{uuid}")
  public String deleteInventoryItem(@PathVariable String uuid, Model model) {
    InventoryItem item = inventoryItemService.findByUuid(uuid);
    inventoryItemService.delete(item);
    model.addAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(0, 50)));
    model.addAttribute(new ToastMessage(MessageType.SUCCESS, "notifications.inventory.deleteSuccess"));
    return VIEW_OVERVIEW;
  }

  @GetMapping("admin/inventory/new")
  public String inventoryNew(Model model) {
    model.addAttribute("itemCategories", itemCategoryService.findAllCategories());
    model.addAttribute("inventoryItemDto", new InventoryItemDto());
    return VIEW_NEW_ITEM;
  }

  @PostMapping("admin/inventory/new")
  public String newInventoryItem(@Valid @ModelAttribute InventoryItemDto inventoryItemDto,
      BindingResult bindingResult, Model model) {
    model.addAttribute(inventoryItemDto);
    model.addAttribute("itemCategories", itemCategoryService.findAllCategories());
    if (bindingResult.hasErrors()) {
      return VIEW_NEW_ITEM;
    }
    inventoryItemService.createNewItem(
        inventoryItemDto.getItemCategory(),
        inventoryItemDto.getDescription(),
        inventoryItemDto.isActive(),
        getVimSession().getActiveUser()
    );
    PageRequest paging = PageRequest.of(0, 50);
    model.addAttribute("itemsList", inventoryItemService.findAll(paging));
    model.addAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.inventory.newItemSuccess",
            true));
    return inventory(paging.getPageNumber(), model);
  }

}
