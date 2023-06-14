package pl.daveproject.frontendservice.applicationLayout;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/empty", layout = BeforeLoginAppLayout.class)
public class EmptyView extends VerticalLayout {
    public EmptyView() {
        add(new H1("Pusty widok"));
    }
}
