package pl.daveproject.frontendservice.ui.recipe;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.ui.component.CrudToolbar;
import pl.daveproject.frontendservice.ui.layout.AfterLoginAppLayout;

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
