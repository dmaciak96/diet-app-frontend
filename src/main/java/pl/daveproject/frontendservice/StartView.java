package pl.daveproject.frontendservice;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import pl.daveproject.frontendservice.dashboard.DashboardView;
import pl.daveproject.frontendservice.layout.BeforeLoginAppLayout;

@Slf4j
@AnonymousAllowed
@Route(value = "/start", layout = BeforeLoginAppLayout.class)
@RouteAlias(value = "/", layout = BeforeLoginAppLayout.class)
public class StartView extends VerticalLayout implements HasDynamicTitle {

    private final String authorizationEndpoint;

    public StartView(@Value("${authorization-endpoint}") String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
        this.setWidthFull();
        this.setHeightFull();
        this.setAlignItems(Alignment.CENTER);

        var logo = getLogo();
        var loginButton = getLoginButton();
        this.add(logo, loginButton);
    }

    private Image getLogo() {
        var imageResource = new StreamResource("logo.png",
                () -> getClass().getResourceAsStream("/img/logo.png"));
        var image = new Image(imageResource, "Webdiet logo");
        image.addClickListener(e -> UI.getCurrent().navigate(DashboardView.class));
        image.addClassName(LumoUtility.Margin.Top.LARGE);
        return image;
    }

    private VerticalLayout getLoginButton() {
        var button = new Button(getTranslation("login-page.login"),
                clickEvent -> UI.getCurrent().getPage().setLocation(authorizationEndpoint));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.setWidth("30%");
        button.setHeight("6%");

        var layout = new VerticalLayout(button);
        layout.setWidthFull();
        layout.setHeightFull();
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setAlignItems(Alignment.CENTER);
        return layout;
    }

    @Override
    public String getPageTitle() {
        return getTranslation("application.name");
    }
}
