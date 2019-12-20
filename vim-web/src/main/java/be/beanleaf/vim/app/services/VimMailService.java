package be.beanleaf.vim.app.services;

import be.beanleaf.vim.services.MailService;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class VimMailService {

  private final TemplateEngine templateEngine;
  private final MailService mailService;

  @Value("${spring.mvc.locale:en}")
  private String defaultLanguageTag;

  @Autowired
  public VimMailService(TemplateEngine templateEngine,
      MailService mailService) {
    this.templateEngine = templateEngine;
    this.mailService = mailService;
  }

  private Context prepareContext(Map<String, Object> variables, String subject,
      String languageTag) {
    variables.put("mailSubject", subject);
    Locale locale = Locale
        .forLanguageTag(StringUtils.isEmpty(languageTag) ? defaultLanguageTag : languageTag);
    variables.put("htmlLang", locale.getLanguage());
    final Context context = new Context(locale);
    context.setVariables(variables);
    return context;
  }

  public void sendMail(String template, String subject, String to, Map<String, Object> variables,
      String languageTag)
      throws MessagingException {
    sendMail(template, subject, Collections.singletonList(to), variables, languageTag);
  }

  public void sendMail(String template, String subject, List<String> recipients,
      Map<String, Object> variables, String languageTag)
      throws MessagingException {
    String text = templateEngine.process(template, prepareContext(variables, subject, languageTag));
    mailService.sendMessage(recipients, subject, text, true);
  }
}
