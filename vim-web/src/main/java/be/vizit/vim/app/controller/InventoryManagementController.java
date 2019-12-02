package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.dto.InventoryItemDto;
import be.vizit.vim.app.utils.MessageType;
import be.vizit.vim.app.utils.ToastMessage;
import be.vizit.vim.app.utils.VimDataTable;
import be.vizit.vim.app.utils.VimDataTableColumn;
import be.vizit.vim.app.utils.WebUtils;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.services.InventoryItemService;
import be.vizit.vim.services.ItemCategoryService;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public String inventory(@RequestParam(required = false) Integer page, Model model) {
    VimDataTable<InventoryItem> table = new VimDataTable<InventoryItem>(page, 15, URL_OVERVIEW) {
      @Override
      public int getCount() {
        return inventoryItemService.findAll(Pageable.unpaged()).size(); //TODO: replace this
      }

      @Override
      public List<InventoryItem> getData() {
        return inventoryItemService.findAll(getPageRequest());
      }

      @Override
      protected List<VimDataTableColumn<InventoryItem>> getColumns() {
        return Arrays.asList(
            new VimDataTableColumn<InventoryItem>("inventory.item.id") {
              @Override
              protected Long getValue(InventoryItem object) {
                return object.getId();
              }
            },
            new VimDataTableColumn<InventoryItem>("inventory.item.description") {
              @Override
              protected String getText(InventoryItem object) {
                return object.getDescription();
              }
            },
            new VimDataTableColumn<InventoryItem>("inventory.item.brand") {
              @Override
              protected String getText(InventoryItem object) {
                return object.getBrand();
              }
            },
            new VimDataTableColumn<InventoryItem>("inventory.item.active") {
              @Override
              public boolean isRawHtml() {
                return true;
              }

              @Override
              protected String getText(InventoryItem object) {
                return object.isActive()
                    ? WebUtils.icon("octicon octicon-check text-green")
                    : WebUtils.icon("octicon octicon-x");
              }
            },
            new VimDataTableColumn<InventoryItem>("inventory.item.addedBy") {
              @Override
              protected String getText(InventoryItem object) {
                return object.getAddedByUser().getShortName();
              }
            },
            new VimDataTableColumn<InventoryItem>("inventory.item.status") {
              @Override
              protected String getText(InventoryItem object) {
                return getLocaleString(
                    "inventory.item.status." + object.getCurrentStatus().getDescription());
              }
            },
            new VimDataTableColumn<InventoryItem>("actions") {

              @Override
              public boolean isRawHtml() {
                return true;
              }

              @Override
              protected String getText(InventoryItem object) {
                String pencilIcon = WebUtils.icon("octicon octicon-pencil");
                String trashcanIcon = WebUtils.icon("octicon octicon-trashcan");
                return
                    WebUtils.link(URL_EDIT_ITEM + "/" + object.getId(), "btn-octicon", pencilIcon) +
                        WebUtils.link(URL_DELETE_ITEM + "/" + object.getId(),
                            "btn-octicon btn-octicon-danger", trashcanIcon);
              }
            }
        );
      }
    };
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
