package be.vizit.vim.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"be.vizit.vim"})
public class VimWebApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(VimWebApp.class);

	public static void main(String[] args) {
        SpringApplication.run(VimWebApp.class, args);
        LOGGER.info("VIM web application has started");
	}
}
