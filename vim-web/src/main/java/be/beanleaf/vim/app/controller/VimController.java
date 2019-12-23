package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.utils.FeedbackUtils;
import be.beanleaf.vim.app.utils.Feedbackmessage;
import be.beanleaf.vim.app.utils.LocaleUtils;
import be.beanleaf.vim.domain.utilities.ValidationException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public abstract class VimController {

  private final static Logger logger = LoggerFactory.getLogger(VimController.class);
  private final VimSession vimSession;
  private final List<String> supportedLocales = Arrays.asList("nl", "en");

  @Value("${spring.messages.encoding:UTF-8}")
  private String defaultEncoding;

  @Autowired
  VimController(VimSession vimSession) {
    this.vimSession = vimSession;
  }

  VimSession getVimSession() {
    return vimSession;
  }

  private Map<String, Object> getDefaultModelAttributes() {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("defaultEncoding", defaultEncoding);
    attributes.put("htmlLang", getLocale().getLanguage());
    attributes.put("activeUser", vimSession.getActiveUser());
    attributes.put("supportedLanguageTags", supportedLocales);
    return attributes;
  }

  @ModelAttribute
  public void addModelAttributes(Model model) {
    model.addAllAttributes(getDefaultModelAttributes());
  }

  @ExceptionHandler(Exception.class)
  public String addException(Exception ex, HttpServletRequest request,
      RedirectAttributes redirectAttributes) {
    if (!(ex instanceof ValidationException)) {
      logger.error(ExceptionUtils.getStackTrace(ex));
    }
    Feedbackmessage feedbackMessage = FeedbackUtils.createMessage(ex).setCloseable(true);
    redirectAttributes.addFlashAttribute(feedbackMessage);
    String referer = request.getHeader("Referer");
    return redirect(referer == null ? HomeController.URL_HOME : referer);
  }

  public String getLocaleString(String key) {
    return LocaleUtils.getLocalString(getLocale(), "messages/messages", key);
  }

  public Locale getLocale() {
    Locale userLocale = LocaleUtils.getUserLocale(getVimSession().getActiveUser());
    if (userLocale != null) {
      return userLocale;
    }
    return LocaleContextHolder.getLocale();
  }

  String redirect(String view) {
    return "redirect:" + view;
  }
}
