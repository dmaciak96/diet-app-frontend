package pl.daveproject.frontendservice.applicationLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.daveproject.frontendservice.login.LoginView;
import pl.daveproject.frontendservice.registration.RegistrationView;

@CssImport("./style.css")
@JsModule("./prefers-color-scheme.js")
public class BeforeLoginAppLayout extends AppLayout {

    public BeforeLoginAppLayout() {
        addToNavbar(createHeader(), createRouterLinks());
    }

    private HorizontalLayout createHeader() {
        var applicationTitle = new H1(getTranslation("application.name"));
        applicationTitle.addClassNames(LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(applicationTitle);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        return header;
    }

    private HorizontalLayout createRouterLinks() {
        var routerLinksLayout = new HorizontalLayout();
        routerLinksLayout.addClassNames(LumoUtility.Width.FULL, LumoUtility.Margin.Right.MEDIUM);
        routerLinksLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        routerLinksLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        routerLinksLayout.add(createRouterLink("login-page.login", LoginView.class));
        routerLinksLayout.add(createRouterLink("register-page.sign-up", RegistrationView.class));
        return routerLinksLayout;
    }

    private Button createRouterLink(String linkTranslationKey, Class<? extends Component> navigationTarget) {
        return new Button(getTranslation(linkTranslationKey),
                clickEvent -> UI.getCurrent().navigate(navigationTarget));
    }
}
