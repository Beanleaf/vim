package be.beanleaf.vim.app.controller;

import be.beanleaf.datatable.DataTable;
import be.beanleaf.datatable.DataTableColumn;
import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.InventoryItemDto;
import be.beanleaf.vim.app.utils.LocaleUtils;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.SelectFilter;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.services.InventoryItemService;
import be.beanleaf.vim.services.ItemCategoryService;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

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
      @RequestParam(required = false) Long cat, @RequestParam(required = false) Integer status,
      Model model) {
    ItemCategory itemCategory =
        cat != null ? itemCategoryService.getItemCategory(cat) : null;
    ItemStatus itemStatus = status != null ? ItemStatus.forId(status) : null;
    DataTable<InventoryItem> table = new DataTable<InventoryItem>(page, 15) {
      @Override
      public long getCount() {
        return inventoryItemService.countAllByStatusAndCategory(itemStatus, itemCategory);
      }

      @Override
      public List<InventoryItem> getData() {
        PageRequest page = PageRequest.of(getCurrentPage(), getPageSize(), Sort.by("description"));
        return inventoryItemService.findAllByCategoryAndStatus(itemCategory, itemStatus, page);
      }

      @Override
      public List<DataTableColumn<InventoryItem>> getColumns() {
        return null;
      }
    };
    model.addAttribute("dataTable", table);
    List<ItemCategory> categories = itemCategoryService.findAllCategories();
    model.addAttribute("categoryFilter",
        new SelectFilter<ItemCategory>("cat", categories, itemCategory) {
          @Override
          public String getValue(ItemCategory object) {
            return object.getId().toString();
          }

          @Override
          public String getText(ItemCategory object) {
            return object.getDescription();
          }
        });
    model.addAttribute("statusFilter",
        new SelectFilter<ItemStatus>("status", Arrays.asList(ItemStatus.values()), itemStatus) {
          @Override
          public String getValue(ItemStatus object) {
            return Integer.toString(object.getId());
          }

          @Override
          public String getText(ItemStatus object) {
            return StringUtils
                .capitalize(getLocaleString("inventory.item.status." + object.getDescription()));
          }
        });
    for (ItemStatus statusEnum : ItemStatus.values()) {
      String attributeName = "amount" + statusEnum.name();
      model.addAttribute(attributeName, 0);
      if (itemStatus == null || itemStatus == statusEnum) {
        model.addAttribute(attributeName,
            inventoryItemService.countAllByStatusAndCategory(statusEnum, itemCategory)
        );
      }
    }
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_DELETE_ITEM + "/{id}")
  public String deleteInventoryItem(@PathVariable long id, RedirectAttributes redirectAttributes) {
    InventoryItem item = inventoryItemService.getInventoryItem(id);
    inventoryItemService.delete(item);
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
        inventoryItem.getItemCategory(), inventoryItem.getDescription(), inventoryItem.isActive(),
        inventoryItem.getBrand(),
        LocaleUtils.getLocalNumberString(getLocale(), inventoryItem.getValue()),
        inventoryItem.getCurrentStatus()
    ), URL_EDIT_ITEM + "/" + id);
    return VIEW_EDIT_ITEM;
  }

  @PostMapping(URL_EDIT_ITEM + "/{id}")
  public String inventoryItemEditPost(@Valid @ModelAttribute InventoryItemDto inventoryItemDto,
      BindingResult bindingResult, @PathVariable long id, Model model,
      RedirectAttributes redirectAttributes) throws ParseException {
    InventoryItem inventoryItem = inventoryItemService.getInventoryItem(id);
    model.addAttribute("originalItem", inventoryItem);
    setupItemForm(model, inventoryItemDto, URL_EDIT_ITEM + "/" + id);
    if (!bindingResult.hasErrors()) {
      inventoryItemService.updateItem(inventoryItem,
          inventoryItemDto.getItemCategory(), inventoryItemDto.getDescription(),
          inventoryItemDto.isActive(), inventoryItemDto.getBrand(),
          LocaleUtils.getLocalDouble(getLocale(), inventoryItemDto.getValue()),
          inventoryItemDto.getStatus()
      );
      redirectAttributes.addFlashAttribute(new ToastMessage(MessageType.SUCCESS,
          "notifications.inventory.editItemSuccess", true));
      return redirect(URL_OVERVIEW);
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
        inventoryItemDto.getItemCategory(), inventoryItemDto.getDescription(),
        inventoryItemDto.isActive(), inventoryItemDto.getBrand(), getVimSession().getActiveUser()
    );
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.inventory.newItemSuccess",
            true));
    return redirect(URL_OVERVIEW);
  }

}
