package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    return render(view, model.asMap());
  }

  ModelAndView render(String view, BindingResult bindingResult) {
    return new ModelAndView(view, bindingResult.getModel());
  }

  private ModelAndView render(String view, Map<String, Object> modelMap) {
    return new ModelAndView(view, modelMap);
  }

  ModelAndView redirect(String view) {
    return new ModelAndView("redirect:" + view);
  }
}
