package be.beanleaf.vim.app.services;

import be.beanleaf.vim.services.MailService;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
@Profile("!dev")
public class VimProdMailService extends VimAbstractMailService {

  private final TemplateEngine templateEngine;
  private final MailService mailService;

  @Value("${spring.mvc.locale:en}")
  private String defaultLanguageTag;

  @Autowired
  public VimProdMailService(TemplateEngine templateEngine,
      MailService mailService) {
    this.templateEngine = templateEngine;
    this.mailService = mailService;
  }

  @Override
  public void sendMail(String template, String subject, List<String> recipients,
      Map<String, Object> variables, String languageTag)
      throws MessagingException {
    String text = templateEngine.process(template,
        prepareContext(
            variables,
            subject,
            StringUtils.isEmpty(languageTag) ? defaultLanguageTag : languageTag
        ));
    mailService.sendMessage(recipients, subject, text, true);
  }
}
