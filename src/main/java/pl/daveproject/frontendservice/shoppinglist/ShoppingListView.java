package pl.daveproject.frontendservice.shoppinglist;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.component.CrudToolbar;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

@Route(value = "/shopping-lists", layout = AfterLoginAppLayout.class)
public class ShoppingListView extends VerticalLayout implements HasDynamicTitle {

    public ShoppingListView() {
        add(new CrudToolbar());
    }

    @Override
    public String getPageTitle() {
        return getTranslation("shopping-lists-page.title");
    }
}
