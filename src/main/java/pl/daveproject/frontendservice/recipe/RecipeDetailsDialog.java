package pl.daveproject.frontendservice.recipe;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.recipe.model.Recipe;

@Slf4j
public class RecipeDetailsDialog extends CloseableDialog implements Translator {

  public RecipeDetailsDialog(Recipe recipe) {
    super(recipe.getName(), false);
  }
}
