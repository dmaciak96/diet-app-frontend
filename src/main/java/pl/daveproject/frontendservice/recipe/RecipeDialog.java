package pl.daveproject.frontendservice.recipe;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.service.RecipeService;

@Slf4j
public class RecipeDialog extends CloseableDialog implements Translator {

  private final WebdietFormWrapper recipeform;
  private final RecipeService recipeService;

  public RecipeDialog(RecipeService recipeService, Recipe recipe) {
    super("recipes-window.title");
    this.recipeService = recipeService;
    this.recipeform = new WebdietFormWrapper(new RecipeForm(recipe), false);
    add(recipeform);
    saveOrUpdateRecipeOnSave();
  }

  private void saveOrUpdateRecipeOnSave() {
    var form = (RecipeForm) recipeform.getFormLayout();
    form.addSaveListener(e -> {
      log.info("Creating/Updating recipe: {}", e.getRecipe().getName());
      var createdProduct = recipeService.saveOrUpdate(e.getRecipe());
      log.info("Recipe {} successfully created/updated", createdProduct.getName());
      this.close();
    });
  }
}
