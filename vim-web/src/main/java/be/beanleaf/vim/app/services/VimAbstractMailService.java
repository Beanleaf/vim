package be.beanleaf.vim.app.services;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import org.thymeleaf.context.Context;

public abstract class VimAbstractMailService implements VimMailService {

  protected Context prepareContext(Map<String, Object> variables, String subject,
      String languageTag) {
    variables.put("mailSubject", subject);
    Locale locale = Locale.forLanguageTag(languageTag);
    variables.put("htmlLang", locale.getLanguage());
    final Context context = new Context(locale);
    context.setVariables(variables);
    return context;
  }

  @Override
  public void sendMail(String template, String subject, String to, Map<String, Object> variables,
      String languageTag) throws MessagingException {
    sendMail(template, subject, Collections.singletonList(to), variables, languageTag);
  }
}
