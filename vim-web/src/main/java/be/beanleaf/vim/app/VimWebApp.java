package be.beanleaf.vim.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;


public class VimWebApp {

  @Value("${spring.messages.encoding:UTF-8}")
  private String encoding;

  private static final String messagePath = "classpath:/messages/";

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        messagePath + "messages",
        messagePath + "messages.mail",
        messagePath + "messages.errors");
    messageSource.setDefaultEncoding(encoding);
    return messageSource;
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(messagePath + "validations");
    messageSource.setDefaultEncoding(encoding);
    bean.setValidationMessageSource(messageSource);
    return bean;
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new VimLocaleResolver();
  }

  @Bean
  public Formatter<LocalDate> localDateFormatter() {
    return new Formatter<>() {
      @Override
      public LocalDate parse(String s, Locale locale) {
        return LocalDate.parse(s, DateTimeFormatter.ISO_DATE);
      }

      @Override
      public String print(LocalDate localDate, Locale locale) {
        return DateTimeFormatter.ISO_DATE.format(localDate);
      }
    };
  }

//  public static void main(String[] args) {
//    SpringApplication.run(VimWebApp.class, args);
//  }
}
