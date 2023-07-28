package pl.daveproject.frontendservice.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import pl.daveproject.frontendservice.dashboard.DashboardView;
import pl.daveproject.frontendservice.registration.RegistrationView;

public class BeforeLoginAppLayout extends AbstractAppLayout {

  private static final String keycloakAuthenticationEndpoint = "protocol/openid-connect/auth";

  private String keycloakAuthentication = "%s/%s?client_id=%s&response_type=%s&scope=%s&redirect_uri=%s&state=%s";

  public BeforeLoginAppLayout(
      @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}") String keycloakUrl,
      @Value("${spring.security.oauth2.client.registration.keycloak.client-id}") String clientId,
      @Value("${spring.security.oauth2.client.registration.keycloak.scope}") List<String> scopes,
      @Value("${spring.security.oauth2.redirect-uri}") String redirectUri) {
    super();
    var state = UUID.randomUUID().toString();
    var responseType = "code";
    this.keycloakAuthentication = this.keycloakAuthentication.formatted(keycloakUrl,
        keycloakAuthenticationEndpoint, clientId,
        responseType,
        String.join(StringUtils.SPACE, scopes), redirectUri, state);
    addToNavbar(createRouterLinks());
  }

  private HorizontalLayout createRouterLinks() {
    var routerLinksLayout = new HorizontalLayout();
    routerLinksLayout.addClassNames(LumoUtility.Width.FULL, LumoUtility.Margin.Right.MEDIUM);
    routerLinksLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    routerLinksLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

    routerLinksLayout.add(createRouterLink("login-page.login", DashboardView.class));
    routerLinksLayout.add(createRouterLink("register-page.sign-up", RegistrationView.class));
    return routerLinksLayout;
  }

  private Button createRouterLink(String linkTranslationKey,
      Class<? extends Component> navigationTarget) {
    return new Button(getTranslation(linkTranslationKey),
        clickEvent -> UI.getCurrent().navigate(navigationTarget));
  }

  private Button createRouterLink(String linkTranslationKey, String url) {
    return new Button(getTranslation(linkTranslationKey),
        clickEvent -> UI.getCurrent().getPage().executeJs("window.location.replace(\"" + url + "\");"));
  }
}
