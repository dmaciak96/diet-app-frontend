package pl.daveproject.frontendservice.bmi;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

@PermitAll
@Route(value = "/bmi", layout = AfterLoginAppLayout.class)
public class BmiView extends VerticalLayout implements HasDynamicTitle {

    @Override
    public String getPageTitle() {
        return getTranslation("bmi-view.title");
    }
}
