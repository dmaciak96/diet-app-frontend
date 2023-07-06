package pl.daveproject.frontendservice.ui.product;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.ui.component.CrudToolbar;
import pl.daveproject.frontendservice.ui.layout.AfterLoginAppLayout;

@Route(value = "/products", layout = AfterLoginAppLayout.class)
public class ProductView extends VerticalLayout implements HasDynamicTitle {

    public ProductView() {
        add(new CrudToolbar());
    }

    @Override
    public String getPageTitle() {
        return getTranslation("products-page.title");
    }
}
