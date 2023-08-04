package pl.daveproject.frontendservice.shoppinglist.model;

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
import pl.daveproject.frontendservice.recipe.model.Recipe;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDialogModel {

  private UUID id;

  @NotBlank
  @Size(min = 1, max = 255)
  private String name;

  @NotNull
  private List<Recipe> recipes;
}
