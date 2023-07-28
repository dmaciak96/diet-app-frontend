package pl.daveproject.frontendservice.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Value;

public class BeforeLoginAppLayout extends AbstractAppLayout {

  private final String authorizationEndpoint;

  public BeforeLoginAppLayout(
      @Value("${keycloak.authorization-endpoint}") String authorizationEndpoint) {
    super();
    this.authorizationEndpoint = authorizationEndpoint;
    addToNavbar(createRouterLinks());
  }

  private HorizontalLayout createRouterLinks() {
    var routerLinksLayout = new HorizontalLayout();
    routerLinksLayout.addClassNames(LumoUtility.Width.FULL, LumoUtility.Margin.Right.MEDIUM);
    routerLinksLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    routerLinksLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    routerLinksLayout.add(createRouterLink("login-page.login", authorizationEndpoint));
    return routerLinksLayout;
  }

  private Button createRouterLink(String linkTranslationKey, String url) {
    return new Button(getTranslation(linkTranslationKey),
        clickEvent -> UI.getCurrent().getPage().setLocation(url));
  }
}
