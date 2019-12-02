package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.dto.InventoryItemDto;
import be.vizit.vim.app.utils.MessageType;
import be.vizit.vim.app.utils.ToastMessage;
import be.vizit.vim.domain.entities.InventoryItem;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InventoryManagementController extends VimController {

  private static final String VIEW_NEW_ITEM = "/admin/inventory/newInventoryItem";
  private static final String URL_NEW_ITEM = "/admin/inventory/new";
  private static final String VIEW_EDIT_ITEM = "/admin/inventory/editInventoryItem";
  private static final String URL_EDIT_ITEM = "/admin/inventory/edit";
  private static final String VIEW_OVERVIEW = "/admin/inventory/overview";
  private static final String URL_OVERVIEW = "/admin/inventory";

  private final InventoryItemService inventoryItemService;
  private final ItemCategoryService itemCategoryService;

  @Autowired
  public InventoryManagementController(VimSession vimSession,
      InventoryItemService inventoryItemService, ItemCategoryService itemCategoryService) {
    super(vimSession);
    this.inventoryItemService = inventoryItemService;
    this.itemCategoryService = itemCategoryService;
  }

  @GetMapping({URL_OVERVIEW, URL_OVERVIEW + "/{page}"})
  public String inventory(@PathVariable(required = false) Integer page, Model model) {
    int startPage = page != null ? page : 0;
    model.addAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(startPage, 50)));
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_OVERVIEW + "/delete/{uuid}")
  public String deleteInventoryItem(@PathVariable String uuid,
      RedirectAttributes redirectAttributes) {
    InventoryItem item = inventoryItemService.findByUuid(uuid);
    inventoryItemService.delete(item);
    redirectAttributes
        .addFlashAttribute("itemsList", inventoryItemService.findAll(PageRequest.of(0, 50)));
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.inventory.deleteSuccess"));
    return redirect(URL_OVERVIEW);
  }

  private void setupItemForm(Model model, InventoryItemDto inventoryItemDto, String action) {
    model.addAttribute(inventoryItemDto);
    model.addAttribute("frmAction", action);
    model.addAttribute("itemCategories", itemCategoryService.findAllCategories());
  }

  @GetMapping(URL_EDIT_ITEM + "/{id}")
  public String inventoryItemEdit(@PathVariable long id, Model model) {
    InventoryItem inventoryItem = inventoryItemService.getInventoryItem(id);
    model.addAttribute("originalItem", inventoryItem);
    setupItemForm(model, new InventoryItemDto(
        inventoryItem.getItemCategory(),
        inventoryItem.getDescription(),
        inventoryItem.isActive()
    ), URL_EDIT_ITEM + "/" + id);
    return VIEW_EDIT_ITEM;
  }

  @PostMapping(URL_EDIT_ITEM + "/{id}")
  public String inventoryItemEditPost(@PathVariable long id, Model model,
      @Valid @ModelAttribute InventoryItemDto inventoryItemDto, BindingResult bindingResult) {
    InventoryItem inventoryItem = inventoryItemService.getInventoryItem(id);
    model.addAttribute("originalItem", inventoryItem);
    setupItemForm(model, inventoryItemDto, URL_EDIT_ITEM + "/" + id);
    if (!bindingResult.hasErrors()) {
      inventoryItem.setActive(inventoryItemDto.isActive());
      inventoryItem.setDescription(inventoryItemDto.getDescription());
      inventoryItem.setItemCategory(inventoryItemDto.getItemCategory());
      inventoryItemService.save(inventoryItem);
      model.addAttribute(new ToastMessage(MessageType.SUCCESS,
          "notifications.inventory.editItemSuccess", false));
    }
    return VIEW_EDIT_ITEM;
  }

  @GetMapping(URL_NEW_ITEM)
  public String inventoryItemNew(Model model) {
    setupItemForm(model, new InventoryItemDto(), URL_NEW_ITEM);
    return VIEW_NEW_ITEM;
  }

  @PostMapping(URL_NEW_ITEM)
  public String inventoryItemNewPost(@Valid @ModelAttribute InventoryItemDto inventoryItemDto,
      BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
    setupItemForm(model, inventoryItemDto, URL_NEW_ITEM);
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
    redirectAttributes.addFlashAttribute("itemsList", inventoryItemService.findAll(paging));
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.inventory.newItemSuccess",
            true));
    return redirect(URL_OVERVIEW);
  }

}
