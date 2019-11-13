package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public abstract class VimController {

  private final VimSession vimSession;

  @Autowired
  VimController(VimSession vimSession) {
    this.vimSession = vimSession;
  }

  VimSession getVimSession() {
    return vimSession;
  }

  @ModelAttribute
  public void addModelAttributes(Model model) {
    model.addAttribute("activeUser", vimSession.getActiveUser());
  }
}
