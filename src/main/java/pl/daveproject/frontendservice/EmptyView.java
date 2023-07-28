package pl.daveproject.frontendservice;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.daveproject.frontendservice.layout.BeforeLoginAppLayout;

@PermitAll
@Route(value = "/empty", layout = BeforeLoginAppLayout.class)
public class EmptyView extends VerticalLayout {
    public EmptyView() {
        add(new H1("Pusty widok"));
    }
}
