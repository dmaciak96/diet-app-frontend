package pl.daveproject.frontendservice.dashboard;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

@PermitAll
@Route(value = "/dashboard", layout = AfterLoginAppLayout.class)
public class DashboardView extends VerticalLayout implements HasDynamicTitle {

    @Override
    public String getPageTitle() {
        return getTranslation("dashboard-page.title");
    }
}
