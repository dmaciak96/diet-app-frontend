package pl.daveproject.frontendservice;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Theme(value = Lumo.DARK)
@PWA(name = "Webdiet", shortName = "Webdiet")
public class WebdietFrontendServiceApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(WebdietFrontendServiceApplication.class, args);
    }

}
