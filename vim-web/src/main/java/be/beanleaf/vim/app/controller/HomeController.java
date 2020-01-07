package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.ChartDto;
import be.beanleaf.vim.app.dto.ChartPointDto;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.services.InventoryLogService;
import java.text.DateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    List<ChartPointDto> points = new ArrayList<>();
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, getLocale());
    for (int i = 0; i <= 30; i++) {
      Date date = Date.from(today.minus(Duration.ofDays(i)));
      String label = df.format(date);
      long count = inventoryLogService.countLogsByDate(date);
      if (count > 0) {
        points.add(new ChartPointDto(label, count));
      }
    }
    ChartDto chartDto = new ChartDto("logs", points, "bar", true);
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
