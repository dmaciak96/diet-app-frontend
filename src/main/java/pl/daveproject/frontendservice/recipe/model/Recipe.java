package pl.daveproject.frontendservice.recipe.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

  private UUID id;

  private String name;

  private String description;

  private RecipeType type;

  private List<RecipeProductEntry> products;

  public double getKcal() {
    if (products == null || products.isEmpty()) {
      return 0.0;
    }
    return products.stream()
        .map(this::getKcalForProduct)
        .reduce(Double::sum)
        .orElse(0.0);
  }

  private double getKcalForProduct(RecipeProductEntry recipeProductEntry) {
    if (recipeProductEntry == null || recipeProductEntry.getProduct() == null
        || recipeProductEntry.getProduct().getKcal() == null) {
      return 0.0;
    }
    return (recipeProductEntry.getAmountInGrams()
        * recipeProductEntry.getProduct().getKcal()) / 100;
  }
}
