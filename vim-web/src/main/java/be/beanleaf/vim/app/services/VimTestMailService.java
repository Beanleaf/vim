package be.beanleaf.vim.app.services;

import be.beanleaf.vim.app.VimProperties;
import be.beanleaf.vim.services.MailService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
@Profile("dev")
public class VimTestMailService extends VimAbstractMailService {

  private final TemplateEngine templateEngine;
  private final MailService mailService;
  private final VimProperties vimProperties;

  @Autowired
  public VimTestMailService(TemplateEngine templateEngine,
      MailService mailService, VimProperties vimProperties) {
    this.templateEngine = templateEngine;
    this.mailService = mailService;
    this.vimProperties = vimProperties;
  }

  @Override
  public void sendMail(String template, String subject, List<String> recipients,
      Map<String, Object> variables, String languageTag) throws MessagingException {

    String catchMailAddress = vimProperties.getCatchMailAddress();
    if (!StringUtils.isEmpty(catchMailAddress)) {
      String newSubject = "[Test](to=" + recipients + ") " + subject;
      String text = templateEngine
          .process(template, prepareContext(variables, subject, languageTag));
      mailService.sendMessage(Collections.singletonList(catchMailAddress), newSubject, text, true);
    }
  }
}
