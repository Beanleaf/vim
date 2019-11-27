package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.utils.FeedbackUtils;
import be.vizit.vim.domain.utilities.ValidationException;
import java.util.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class VimController {

  private final static Logger logger = LoggerFactory.getLogger(VimController.class);
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

  @ExceptionHandler(Exception.class)
  public void addException(Exception ex, Model model) {
    if (!(ex instanceof ValidationException)) {
      logger.error(ExceptionUtils.getStackTrace(ex));
    }
    model.addAttribute(FeedbackUtils.createMessage(ex));
    render("/", model);
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

  ModelAndView redirect(String view, Model model) {
    return render("redirect:"+view, model);
  }
}
