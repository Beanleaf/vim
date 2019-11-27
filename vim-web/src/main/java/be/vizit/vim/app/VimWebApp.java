package be.vizit.vim.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication(scanBasePackages = "be.vizit")
@EnableJpaRepositories(basePackages = "be.vizit")
@EntityScan(basePackages = "be.vizit")
public class VimWebApp extends SpringBootServletInitializer {

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:validations");
    messageSource.setDefaultEncoding("UTF-8");
    bean.setValidationMessageSource(messageSource);
    return bean;
  }

  public static void main(String[] args) {
    SpringApplication.run(VimWebApp.class, args);
  }
}
