package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.app.utils.FeedbackUtils;
import be.vizit.vim.domain.utilities.ValidationException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  private void addDefaultModelAttibutes(Model model) {
    model.addAttribute("htmlLang", LocaleContextHolder.getLocale().getLanguage());
    model.addAttribute("activeUser", vimSession.getActiveUser());
  }

  @ModelAttribute
  public void addModelAttributes(Model model) {
    addDefaultModelAttibutes(model);
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView addException(Exception ex, Model model) {
    if (!(ex instanceof ValidationException)) {
      logger.error(ExceptionUtils.getStackTrace(ex));
    }
    addDefaultModelAttibutes(model);
    model.addAttribute(FeedbackUtils.createMessage(ex).setCloseable(true));
    return render(HomeController.VIEW_HOME, model);
  }

  public String getLocaleString(String key) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/messages", getLocale());
    return resourceBundle.getString(key);
  }

  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

  ModelAndView render(String view, Model model) {
    return render(view, model.asMap());
  }

  private ModelAndView render(String view, Map<String, Object> modelMap) {
    return new ModelAndView(view, modelMap);
  }

  String redirect(String view) {
    return "redirect:" + view;
  }
}
