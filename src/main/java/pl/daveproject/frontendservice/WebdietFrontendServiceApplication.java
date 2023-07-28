package pl.daveproject.frontendservice;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Webdiet", shortName = "Webdiet")
public class WebdietFrontendServiceApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(WebdietFrontendServiceApplication.class, args);
    }
}
