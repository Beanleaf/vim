package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.ChartDataSetDto;
import be.beanleaf.vim.app.dto.ChartDto;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.services.InventoryLogService;
import java.text.DateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends VimController {

  public static final String URL_HOME = "/";
  public static final String VIEW_HOME = "public/home";

  private final InventoryLogService inventoryLogService;

  @Autowired
  public HomeController(VimSession vimSession, InventoryLogService inventoryLogService) {
    super(vimSession);
    this.inventoryLogService = inventoryLogService;
  }

  private void addRecentLogsGraph(Model model) {
    Instant today = Instant.now();
    ChartDto chartDto = new ChartDto("bar", true);
    ChartDataSetDto logsDataSet = new ChartDataSetDto("logs");
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, getLocale());
    for (int i = 0; i <= 30; i++) {
      Date date = Date.from(today.minus(Duration.ofDays(i)));
      long count = inventoryLogService.countLogsByDate(date);
      if (count > 0) {
        chartDto.addLabel(df.format(date));
        logsDataSet.addData(count, "#b392f0", "#6f42c1");
      }
    }
    chartDto.setDatasets(Collections.singletonList(logsDataSet));
    chartDto.setLegendDisplay(false);
    model.addAttribute("chart", chartDto);
  }

  @GetMapping(value = {URL_HOME, "/home"})
  public String home(Model model) {
    addRecentLogsGraph(model);
    return VIEW_HOME;
  }

  @GetMapping(value = URL_HOME, params = {"logout"})
  public String succesfullLogout(Model model) {
    addRecentLogsGraph(model);
    model.addAttribute(new ToastMessage(MessageType.INFO, "notifications.loggedout"));
    return VIEW_HOME;
  }
}
