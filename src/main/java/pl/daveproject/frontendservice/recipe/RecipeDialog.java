package pl.daveproject.frontendservice.recipe;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;
import pl.daveproject.frontendservice.product.ProductDialog;
import pl.daveproject.frontendservice.product.service.ProductService;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.service.RecipeService;

@Slf4j
public class RecipeDialog extends CloseableDialog implements Translator {

  private final WebdietFormWrapper recipeform;
  private final RecipeService recipeService;
  private final ProductService productService;

  public RecipeDialog(RecipeService recipeService, Recipe recipe, ProductService productService) {
    super("recipes-window.title");
    this.recipeService = recipeService;
    this.productService = productService;
    this.recipeform = new WebdietFormWrapper(new RecipeForm(recipe), false);
    add(recipeform);
    saveOrUpdateRecipeOnSave();
  }

  private void saveOrUpdateRecipeOnSave() {
    var form = (RecipeForm) recipeform.getFormLayout();
    form.addSaveListener(e -> {
      if(recipeService.isAllProductExists(e.getRecipe().getProducts())) {
        log.info("Creating/Updating recipe: {}", e.getRecipe().getName());
        var createdRecipe = recipeService.saveOrUpdate(e.getRecipe());
        log.info("Recipe {} successfully created/updated", createdRecipe.getName());
        this.close();
      } else {
        var notExistingProducts = recipeService.getNotExistingProducts(e.getRecipe().getProducts());
        for (var product: notExistingProducts) {
          var productDialog = new ProductDialog(productService, product);
          add(productDialog);
          productDialog.open();
        }
      }
    });
  }
}
