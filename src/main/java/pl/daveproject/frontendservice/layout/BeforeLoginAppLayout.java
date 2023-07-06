package pl.daveproject.frontendservice.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.daveproject.frontendservice.login.LoginView;
import pl.daveproject.frontendservice.registration.RegistrationView;

public class BeforeLoginAppLayout extends AbstractAppLayout {

    public BeforeLoginAppLayout() {
        super();
        addToNavbar(createRouterLinks());
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
