package pl.daveproject.frontendservice.recipe.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.daveproject.frontendservice.product.model.Product;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeProductEntry {

  private UUID id;

  private Product product;

  private Double amountInGrams;
}
