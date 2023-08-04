package pl.daveproject.frontendservice.shoppinglist;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Top;
import org.apache.commons.lang3.StringUtils;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.ViewDetailsButton;
import pl.daveproject.frontendservice.recipe.RecipeDetailsDialog;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingList;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingListProductEntry;

public class ShoppingListDetailsDialog extends CloseableDialog implements Translator {

  public ShoppingListDetailsDialog(ShoppingList shoppingList) {
    super("%s (%s kcal)".formatted(shoppingList.getName(), shoppingList.getKcal()), false);

    add(getProductEntryGrid(shoppingList));
    add(getRecipesGrid(shoppingList));
  }

  private Grid<ShoppingListProductEntry> getProductEntryGrid(ShoppingList shoppingList) {
    var shoppingListProductEntryGrid = new Grid<ShoppingListProductEntry>();
    shoppingListProductEntryGrid.addClassNames(Top.MEDIUM);
    shoppingListProductEntryGrid.setItems(shoppingList.getProducts());
    shoppingListProductEntryGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

    shoppingListProductEntryGrid.addColumn(
            recipeProductEntry -> recipeProductEntry.getProduct().getName())
        .setHeader(getTranslation("products-page.grid-label-name"))
        .setSortable(true)
        .setResizable(true);

    shoppingListProductEntryGrid.addColumn(ShoppingListProductEntry::getRoundedAmountInGrams)
        .setHeader(getTranslation("recipe-form-amount-in-grams"))
        .setSortable(true)
        .setResizable(true);
    return shoppingListProductEntryGrid;
  }

  private Grid<Recipe> getRecipesGrid(ShoppingList shoppingList) {
    var recipeGrid = new Grid<Recipe>();
    recipeGrid.setItems(shoppingList.getRecipes());
    recipeGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
    recipeGrid.addClassNames(Top.MEDIUM);

    recipeGrid.addColumn(Recipe::getName)
        .setHeader(getTranslation("recipes-page.grid-label-name"))
        .setSortable(true)
        .setResizable(true);

    recipeGrid.addColumn(Recipe::getRoundedKcal)
        .setHeader(getTranslation("recipes-page.grid-label-kcal"))
        .setSortable(true)
        .setResizable(true);

    recipeGrid.addColumn(recipe -> getTranslation(recipe.getType().getTranslationKey()))
        .setHeader(getTranslation("recipes-page.grid-label-type"))
        .setSortable(true)
        .setResizable(true);

    recipeGrid.addColumn(new ComponentRenderer<>(recipe -> {
          var viewDetailsButton = new ViewDetailsButton();
          viewDetailsButton.addClickListener(e -> {
            var recipeDetailsDialog = new RecipeDetailsDialog(recipe);
            add(recipeDetailsDialog);
            recipeDetailsDialog.open();
          });
          return viewDetailsButton;
        }))
        .setHeader(StringUtils.EMPTY)
        .setSortable(false)
        .setResizable(false);
    return recipeGrid;
  }
}
