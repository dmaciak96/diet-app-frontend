package pl.daveproject.frontendservice.recipe;

import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.grid.GridDataFilter;
import pl.daveproject.frontendservice.recipe.model.Recipe;

@Setter
@Component
public class RecipeFilter implements Translator, GridDataFilter {

  private String searchValue;

  public boolean match(Recipe recipe) {
    var matchesName = matches(recipe.getName(), searchValue);
    var matchesType = matches(getTranslation(recipe.getType().getTranslationKey()), searchValue);
    return matchesName || matchesType;
  }

  private boolean matches(String value, String searchTerm) {
    return searchTerm == null || searchTerm.isEmpty()
        || value.toLowerCase().contains(searchTerm.toLowerCase());
  }
}
