package pl.daveproject.frontendservice;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.layout.BeforeLoginAppLayout;

@Slf4j
@AnonymousAllowed
@Route(value = "/start", layout = BeforeLoginAppLayout.class)
@RouteAlias(value = "/", layout = BeforeLoginAppLayout.class)
public class StartView extends VerticalLayout implements HasDynamicTitle {

  @Override
  public String getPageTitle() {
    return getTranslation("application.name");
  }
}
