package pl.daveproject.frontendservice.recipe;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import pl.daveproject.frontendservice.component.DeleteConfirmDialog;
import pl.daveproject.frontendservice.component.ViewDetailsButton;
import pl.daveproject.frontendservice.component.grid.CrudGrid;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;
import pl.daveproject.frontendservice.product.service.ProductService;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.service.RecipeService;

@PermitAll
@Route(value = "/recipes", layout = AfterLoginAppLayout.class)
public class RecipeView extends VerticalLayout implements HasDynamicTitle {

  private final RecipeService recipeService;
  private final ProductService productService;
  private final CrudGrid<Recipe, RecipeFilter> recipeGrid;

  public RecipeView(RecipeService recipeService,
      ProductService productService, RecipeFilter recipeFilter,
      RecipeDataProvider recipeDataProvider) {
    this.productService = productService;
    this.recipeGrid = new CrudGrid<>(recipeDataProvider, recipeFilter);
    this.recipeService = recipeService;

    createGridColumns();
    setOnNewClickListener();
    setOnEditClickListener();
    setOnDeleteClickListener();
    add(recipeGrid);
  }

  private void createGridColumns() {
    recipeGrid.getGrid().addColumn(Recipe::getName, RecipeDataProvider.NAME_SORTING_KEY)
        .setHeader(getTranslation("recipes-page.grid-label-name"))
        .setSortable(true)
        .setResizable(true);

    recipeGrid.getGrid().addColumn(recipe -> (double) Math.round(recipe.getKcal() * 100) / 100,
            RecipeDataProvider.KCAL_SORTING_KEY)
        .setHeader(getTranslation("recipes-page.grid-label-kcal"))
        .setSortable(true)
        .setResizable(true);

    recipeGrid.getGrid().addColumn(recipe -> getTranslation(recipe.getType().getTranslationKey()),
            RecipeDataProvider.TYPE_SORTING_KEY)
        .setHeader(getTranslation("recipes-page.grid-label-type"))
        .setSortable(true)
        .setResizable(true);

    recipeGrid.getGrid().addColumn(new ComponentRenderer<>(recipe -> {
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
  }

  private void setOnNewClickListener() {
    recipeGrid.addOnClickListener(event ->
        createAndOpenRecipeDialog(Recipe.builder().build()));
  }

  private void setOnEditClickListener() {
    recipeGrid.editOnClickListener(event -> {
      var selectedProduct = recipeGrid.getGrid()
          .getSelectedItems()
          .stream()
          .findFirst();
      selectedProduct.ifPresent(this::createAndOpenRecipeDialog);
    });
  }

  private void createAndOpenRecipeDialog(Recipe recipe) {
    var recipeDialog = new RecipeDialog(recipeService, recipe, productService);
    add(recipeDialog);
    recipeDialog.open();
    recipeDialog.addOpenedChangeListener(e -> {
      if (!e.isOpened()) {
        recipeGrid.refresh();
      }
    });
  }

  private void setOnDeleteClickListener() {
    recipeGrid.deleteOnClickListener(event -> {
      var selectedProduct = recipeGrid.getGrid()
          .getSelectedItems()
          .stream()
          .findFirst();
      selectedProduct.ifPresent(this::createAndOpenProductDeleteDialog);
    });
  }

  private void createAndOpenProductDeleteDialog(Recipe recipe) {
    var confirmDialogSuffix = "%s \"%s\"".formatted(
        getTranslation("delete-dialog.header-recipe-suffix"),
        recipe.getName());
    var confirmDialog = new DeleteConfirmDialog(confirmDialogSuffix);
    confirmDialog.open();
    confirmDialog.addConfirmListener(event -> {
      recipeService.delete(recipe.getId());
      recipeGrid.refresh();
      confirmDialog.close();
    });
  }

  @Override
  public String getPageTitle() {
    return getTranslation("recipes-page.title");
  }
}
