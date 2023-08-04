package pl.daveproject.frontendservice.recipe;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Top;
import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.model.RecipeProductEntry;

@Slf4j
public class RecipeDetailsDialog extends CloseableDialog implements Translator {

  public RecipeDetailsDialog(Recipe recipe) {
    super("%s (%s kcal)".formatted(recipe.getName(), recipe.getRoundedKcal()), false);
    add(new Text(recipe.getDescription()));
    add(getRecipeProductEntryGrid(recipe));
  }

  private Grid<RecipeProductEntry> getRecipeProductEntryGrid(Recipe recipe) {
    var recipeProductEntryGrid = new Grid<RecipeProductEntry>();
    recipeProductEntryGrid.addClassNames(Top.MEDIUM);
    recipeProductEntryGrid.setItems(recipe.getProducts());
    recipeProductEntryGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

    recipeProductEntryGrid.addColumn(recipeProductEntry -> recipeProductEntry.getProduct().getName())
        .setHeader(getTranslation("products-page.grid-label-name"))
        .setSortable(true)
        .setResizable(true);

    recipeProductEntryGrid.addColumn(RecipeProductEntry::getRoundedAmountInGrams)
        .setHeader(getTranslation("recipe-form-amount-in-grams"))
        .setSortable(true)
        .setResizable(true);
    return recipeProductEntryGrid;
  }
}
