package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.services.InventoryItemService;
import be.vizit.vim.services.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InventoryController extends VimController {

  private final InventoryItemService inventoryItemService;
  private final ItemCategoryService itemCategoryService;

  @Autowired
  public InventoryController(VimSession vimSession,
      InventoryItemService inventoryItemService, ItemCategoryService itemCategoryService) {
    super(vimSession);
    this.inventoryItemService = inventoryItemService;
    this.itemCategoryService = itemCategoryService;
  }

  @GetMapping({"/admin/inventory", "/admin/inventory/{page}"})
  public String inventory(@PathVariable(required = false) Integer page, Model model) {
    int startPage = page != null ? page : 0;
    model.addAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(startPage, 50)));
    model.addAttribute("itemCategories", itemCategoryService.findAllCategories());
    return "admin/inventory";
  }
}
