package be.vizit.vim.services;

import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  private final JavaMailSender javaMailSender;

  @Autowired
  public MailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendMessage(List<String> recipients, String subject, String text, boolean isHtml)
      throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper messageHelper = new MimeMessageHelper(message);
    messageHelper.setTo(recipients.toArray(new String[0]));
    messageHelper.setSubject(subject);
    messageHelper.setText(text, isHtml);
    javaMailSender.send(message);
  }
}
