package be.beanleaf.vim.app.services;

import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;

public interface VimMailService {

  void sendMail(String template, String subject, String to, Map<String, Object> variables,
      String languageTag)
      throws MessagingException;

  void sendMail(String template, String subject, List<String> recipients,
      Map<String, Object> variables, String languageTag)
      throws MessagingException;
}
