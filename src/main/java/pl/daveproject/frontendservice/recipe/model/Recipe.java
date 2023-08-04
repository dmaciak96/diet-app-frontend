package pl.daveproject.frontendservice.recipe.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @NotBlank
  @Size(min = 1, max = 255)
  private String name;

  @NotBlank
  @Size(min = 1, max = 2550)
  private String description;

  @NotNull
  private RecipeType type;

  @NotNull
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

  public double getRoundedKcal() {
    return (double) Math.round(getKcal() * 100) / 100;
  }

  private double getKcalForProduct(RecipeProductEntry recipeProductEntry) {
    if (recipeProductEntry == null || recipeProductEntry.getProduct() == null
        || recipeProductEntry.getProduct().getKcal() == null || recipeProductEntry.getAmountInGrams() == null) {
      return 0.0;
    }
    return (recipeProductEntry.getAmountInGrams()
        * recipeProductEntry.getProduct().getKcal()) / 100;
  }
}
