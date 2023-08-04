package pl.daveproject.frontendservice.shoppinglist.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.daveproject.frontendservice.recipe.model.Recipe;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShoppingList {

  private UUID id;

  private String name;

  private List<Recipe> recipes;

  private List<ShoppingListProductEntry> products;

  public double getKcal() {
    if (recipes == null || recipes.isEmpty()) {
      return 0.0;
    }
    var kcal = recipes.stream()
        .map(Recipe::getKcal)
        .reduce(Double::sum)
        .orElse(0.0);
    return (double) Math.round(kcal * 100) / 100;
  }
}
