package be.beanleaf.vim.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "be.beanleaf.vim")
@EnableJpaRepositories(basePackages = "be.beanleaf.vim")
@EntityScan(basePackages = "be.beanleaf.vim")
public class VimApplication {

  public static void main(String[] args) {
    SpringApplication.run(VimApplication.class);
  }
}
