package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

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

  ModelAndView render(String view, Model model) {
    return new ModelAndView(view, model.asMap());
  }

  ModelAndView redirect(String view) {
    return new ModelAndView("redirect:" + view);
  }
}
