package pl.daveproject.frontendservice.recipe;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import pl.daveproject.frontendservice.component.grid.CrudToolbar;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

@PermitAll
@Route(value = "/recipes", layout = AfterLoginAppLayout.class)
public class RecipeView extends VerticalLayout implements HasDynamicTitle {

    public RecipeView() {
        add(new CrudToolbar());
    }

    @Override
    public String getPageTitle() {
        return getTranslation("recipes-page.title");
    }
}
