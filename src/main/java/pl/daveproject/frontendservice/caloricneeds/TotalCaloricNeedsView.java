package pl.daveproject.frontendservice.caloricneeds;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

@PermitAll
@Route(value = "/caloric-needs", layout = AfterLoginAppLayout.class)
public class TotalCaloricNeedsView extends VerticalLayout implements HasDynamicTitle {

    @Override
    public String getPageTitle() {
        return getTranslation("total-caloric-needs.title");
    }
}
