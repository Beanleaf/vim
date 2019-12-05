package be.vizit.vim.app.controller;

import be.beanleaf.datatable.DataTable;
import be.beanleaf.datatable.DataTableColumn;
import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.dto.InventoryItemDto;
import be.vizit.vim.app.utils.MessageType;
import be.vizit.vim.app.utils.SelectFilter;
import be.vizit.vim.app.utils.ToastMessage;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.services.InventoryItemService;
import be.vizit.vim.services.ItemCategoryService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InventoryManagementController extends VimController {

  private static final String VIEW_NEW_ITEM = "/admin/inventory/newInventoryItem";
  private static final String URL_NEW_ITEM = "/admin/inventory/new";
  private static final String VIEW_EDIT_ITEM = "/admin/inventory/editInventoryItem";
  private static final String URL_EDIT_ITEM = "/admin/inventory/edit";
  private static final String VIEW_OVERVIEW = "/admin/inventory/overview";
  private static final String URL_OVERVIEW = "/admin/inventory";
  private static final String URL_DELETE_ITEM = "/admin/inventory/delete";

  private final InventoryItemService inventoryItemService;
  private final ItemCategoryService itemCategoryService;

  @Autowired
  public InventoryManagementController(VimSession vimSession,
      InventoryItemService inventoryItemService, ItemCategoryService itemCategoryService) {
    super(vimSession);
    this.inventoryItemService = inventoryItemService;
    this.itemCategoryService = itemCategoryService;
  }

  @GetMapping(URL_OVERVIEW)
  public String inventory(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Long cat, Model model) {
    DataTable<InventoryItem> table = new DataTable<InventoryItem>(page, 15) {

      private ItemCategory itemCategory =
          cat != null ? itemCategoryService.getItemCategory(cat) : null;

      @Override
      public long getCount() {
        return itemCategory == null ? inventoryItemService.countAllItems()
            : inventoryItemService.countAllItemsInCategory(itemCategory);
      }

      @Override
      public List<InventoryItem> getData() {
        PageRequest page = PageRequest.of(getCurrentPage(), getPageSize());
        return itemCategory == null
            ? inventoryItemService.findAll(page)
            : inventoryItemService.findAllByItemCategory(itemCategory, page);
      }

      @Override
      public List<DataTableColumn<InventoryItem>> getColumns() {
        return null;
      }
    };
    List<ItemCategory> categories = itemCategoryService.findAllCategories();
    model.addAttribute("categoryFilter", new SelectFilter<ItemCategory>("cat", categories) {
      @Override
      public String getValue(ItemCategory object) {
        return object.getId().toString();
      }

      @Override
      public String getText(ItemCategory object) {
        return object.getDescription();
      }
    });
    model.addAttribute("dataTable", table);
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_DELETE_ITEM + "/{id}")
  public String deleteInventoryItem(@PathVariable long id, RedirectAttributes redirectAttributes) {
    InventoryItem item = inventoryItemService.getInventoryItem(id);
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
        inventoryItem.isActive(),
        inventoryItem.getBrand()
    ), URL_EDIT_ITEM + "/" + id);
    return VIEW_EDIT_ITEM;
  }

  @PostMapping(URL_EDIT_ITEM + "/{id}")
  public String inventoryItemEditPost(@Valid @ModelAttribute InventoryItemDto inventoryItemDto,
      BindingResult bindingResult, @PathVariable long id, Model model) {
    InventoryItem inventoryItem = inventoryItemService.getInventoryItem(id);
    model.addAttribute("originalItem", inventoryItem);
    setupItemForm(model, inventoryItemDto, URL_EDIT_ITEM + "/" + id);
    if (!bindingResult.hasErrors()) {
      inventoryItemService.updateItem(inventoryItem,
          inventoryItemDto.getItemCategory(),
          inventoryItemDto.getDescription(),
          inventoryItemDto.isActive(),
          inventoryItemDto.getBrand());
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
    inventoryItemService.saveItemMultipleTimes(inventoryItemDto.getAmount(),
        inventoryItemDto.getItemCategory(),
        inventoryItemDto.getDescription(),
        inventoryItemDto.isActive(),
        inventoryItemDto.getBrand(),
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
