package be.vizit.vim.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(scanBasePackages = "be.vizit")
public class VimWebApp {
	public static void main(String[] args) {
        SpringApplication.run(VimWebApp.class, args);
        System.out.println("VIM started");
	}
}
