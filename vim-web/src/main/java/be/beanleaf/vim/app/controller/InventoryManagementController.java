package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimDataTable;
import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.InventoryItemDto;
import be.beanleaf.vim.app.services.VimPdfService;
import be.beanleaf.vim.app.utils.LocaleUtils;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.SelectFilter;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.app.utils.WebUtils;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.services.InventoryItemService;
import be.beanleaf.vim.services.InventoryLogService;
import be.beanleaf.vim.services.ItemCategoryService;
import be.beanleaf.vim.services.QrService;
import be.beanleaf.vim.utils.DateUtils;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InventoryManagementController extends VimController {

  private static final String VIEW_NEW_ITEM = "admin/inventory/newInventoryItem";
  private static final String URL_NEW_ITEM = "/admin/inventory/new";
  private static final String VIEW_EDIT_ITEM = "admin/inventory/editInventoryItem";
  private static final String URL_EDIT_ITEM = "/admin/inventory/edit";
  private static final String VIEW_OVERVIEW = "admin/inventory/overview";
  private static final String URL_OVERVIEW = "/admin/inventory";
  private static final String URL_DELETE_ITEM = "/admin/inventory/delete";
  private static final String URL_QR = "/admin/qrCode";
  private static final String VIEW_QR = "admin/inventory/qrCode";
  private static final String URL_QR_DOWNLOAD = "/admin/downloadQr";
  private static final String URL_HISTORY = "/admin/inventory/history";
  private static final String VIEW_LOGS = "admin/inventory/logs";
  private static final String URL_DOWNLOAD_QR = "/admin/inventory/downloadQrCodes";

  private final InventoryItemService inventoryItemService;
  private final InventoryLogService inventoryLogService;
  private final ItemCategoryService itemCategoryService;
  private final QrService qrService;
  private final VimPdfService vimPdfService;

  @Autowired
  public InventoryManagementController(VimSession vimSession, QrService qrService,
      InventoryItemService inventoryItemService, InventoryLogService inventoryLogService,
      ItemCategoryService itemCategoryService,
      VimPdfService vimPdfService) {
    super(vimSession);
    this.qrService = qrService;
    this.inventoryItemService = inventoryItemService;
    this.inventoryLogService = inventoryLogService;
    this.itemCategoryService = itemCategoryService;
    this.vimPdfService = vimPdfService;
  }

  @GetMapping(URL_OVERVIEW)
  public String inventory(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Long cat,
      @RequestParam(required = false) Integer status,
      @RequestParam(required = false) String q,
      Model model) {
    ItemCategory itemCategory =
        cat != null ? itemCategoryService.getItemCategory(cat) : null;
    ItemStatus itemStatus = status != null ? ItemStatus.forId(status) : null;
    model.addAttribute("nameFilter", q);
    VimDataTable<InventoryItem> table = new VimDataTable<>(page, 15) {
      @Override
      public long getCount() {
        return inventoryItemService.countItems(q, itemCategory, itemStatus);
      }

      @Override
      public List<InventoryItem> getData() {
        PageRequest page = PageRequest
            .of(getCurrentPage(), getPageSize(), inventoryItemService.getDefaultSort());
        return inventoryItemService.findItems(q, itemCategory, itemStatus, page);
      }
    };
    model.addAttribute("dataTable", table);
    List<ItemCategory> categories = itemCategoryService.findAllCategories();
    model.addAttribute("categoryFilter",
        new SelectFilter<>("cat", categories, itemCategory) {
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
        new SelectFilter<>("status", Arrays.asList(ItemStatus.values()), itemStatus) {
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
            inventoryItemService.countItems(q, itemCategory, statusEnum)
        );
      }
    }
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_DELETE_ITEM + "/{id}")
  public String deleteInventoryItem(
      @PathVariable long id,
      RedirectAttributes redirectAttributes) {
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
  public String inventoryItemEdit(
      @PathVariable long id,
      Model model) {
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
  public String inventoryItemEditPost(
      @Valid @ModelAttribute InventoryItemDto inventoryItemDto,
      BindingResult bindingResult,
      @PathVariable long id,
      Model model,
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
  public String inventoryItemNewPost(
      @Valid @ModelAttribute InventoryItemDto inventoryItemDto,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {
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

  @GetMapping(URL_QR)
  public String getQrString(
      @RequestParam("id") long id,
      @RequestParam("width") int width,
      @RequestParam("height") int height,
      Model model) {
    InventoryItem inventoryItem = inventoryItemService.getInventoryItem(id);
    model.addAttribute("itemId", id);
    model.addAttribute("qrPngSrc", qrService.getQrPngSrc(inventoryItem.getUuid(), width, height));
    return VIEW_QR;
  }

  @GetMapping(URL_QR_DOWNLOAD)
  public ResponseEntity<byte[]> getQrDownloadEntity(
      @RequestParam("id") long id,
      @RequestParam("width") int width,
      @RequestParam("height") int height
  ) {
    InventoryItem inventoryItem = inventoryItemService.getInventoryItem(id);
    byte[] imageSrc = qrService.generateQr(inventoryItem.getUuid(), "png", width, height);
    String filename =
        "qr_" + inventoryItemService.getShortCode(inventoryItem).toLowerCase() + ".png";
    return new ResponseEntity<>(imageSrc, WebUtils.getDownloadHeaders(filename), HttpStatus.OK);
  }


  @GetMapping(URL_DOWNLOAD_QR)
  public ResponseEntity<byte[]> downloadQrCodes() {
    byte[] pdfSrc = vimPdfService.getQrList(inventoryItemService.findAllActiveItems(), 4);
    String filename = "qr_codes.pdf";
    return new ResponseEntity<>(pdfSrc, WebUtils.getDownloadHeaders(filename), HttpStatus.OK);
  }

  @RequestMapping(URL_HISTORY)
  public String getHistory(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) String q,
      @RequestParam(required = false) String dateFrom,
      @RequestParam(required = false) String dateTo,
      Model model) {

    final LocalDateTime from =
        StringUtils.isEmpty(dateFrom) ? null
            : DateUtils.atStartOfDay(LocalDateTime.parse(dateFrom));
    final LocalDateTime to =
        StringUtils.isEmpty(dateTo) ? null
            : DateUtils.atEndOfDay(LocalDateTime.parse(dateTo));
    String search = StringUtils.isEmpty(q) ? null : "%" + q + "%";
    VimDataTable<InventoryLog> logDataTable = new VimDataTable<>(page, 15) {
      @Override
      public long getCount() {
        return inventoryLogService.countLogs(search, from, to);
      }

      @Override
      public List<InventoryLog> getData() {
        PageRequest page = PageRequest
            .of(getCurrentPage(), getPageSize(), inventoryLogService.getDefaultSort());
        return inventoryLogService.searchLogs(search, from, to, page);
      }
    };
    model.addAttribute("searchQ", q);
    model.addAttribute("searchFrom", dateFrom);
    model.addAttribute("searchTo", dateTo);
    List<InventoryLog> unpagedLogs = inventoryLogService
        .searchLogs(search, from, to, Pageable.unpaged());
    model.addAttribute("uniqueAmountOfUsers",
        inventoryLogService.getUniqueUsers(unpagedLogs).size());
    model.addAttribute("amountOfLogs", unpagedLogs.size());
    model.addAttribute("dataTable", logDataTable);
    return VIEW_LOGS;
  }

}
