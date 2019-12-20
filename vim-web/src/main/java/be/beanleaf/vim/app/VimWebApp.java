package be.beanleaf.vim.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

@SpringBootApplication(scanBasePackages = "be.beanleaf.vim")
@EnableJpaRepositories(basePackages = "be.beanleaf.vim")
@EntityScan(basePackages = "be.beanleaf.vim")
public class VimWebApp extends SpringBootServletInitializer {

  @Value("${spring.messages.encoding:UTF-8}")
  private String encoding;

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        "classpath:/messages/messages",
        "classpath:/messages/messages.mail",
        "classpath:/messages/messages.errors");
    messageSource.setDefaultEncoding(encoding);
    return messageSource;
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:validations");
    messageSource.setDefaultEncoding(encoding);
    bean.setValidationMessageSource(messageSource);
    return bean;
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new VimLocaleResolver();
  }

  public static void main(String[] args) {
    SpringApplication.run(VimWebApp.class, args);
  }
}
