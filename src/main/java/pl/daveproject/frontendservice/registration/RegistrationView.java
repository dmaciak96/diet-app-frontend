package pl.daveproject.frontendservice.registration;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.applicationLayout.BeforeLoginAppLayout;

@Route(value = "/register", layout = BeforeLoginAppLayout.class)
public class RegistrationView extends VerticalLayout implements HasDynamicTitle {

    @Override
    public String getPageTitle() {
        return getTranslation("register-page.title");
    }
}
