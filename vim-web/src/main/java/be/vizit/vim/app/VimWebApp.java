package be.vizit.vim.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "be.vizit")
@EnableJpaRepositories(basePackages = "be.vizit")
@EntityScan(basePackages = "be.vizit")
public class VimWebApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(VimWebApp.class);

  public static void main(String[] args) {
    SpringApplication.run(VimWebApp.class, args);
    LOGGER.info("VIM web application has started");
  }
}
