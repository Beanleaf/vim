package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InventoryController extends VimController {

  private static final String URL_IN = "inventory/scan/in";
  private static final String VIEW_IN = "in";

  @Autowired
  public InventoryController(VimSession vimSession) {
    super(vimSession);
  }

  @GetMapping(URL_IN)
  public String scanIn(Model model) {
    return VIEW_IN;
  }
}
