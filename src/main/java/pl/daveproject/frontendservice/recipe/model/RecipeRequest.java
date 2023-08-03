package pl.daveproject.frontendservice.recipe.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecipeRequest {

  @NotBlank
  @Size(min = 1, max = 255)
  private String name;

  @NotBlank
  @Size(min = 1, max = 2550)
  private String description;

  @NotNull
  private RecipeType type;

  @NotNull
  private List<RecipeProductEntryRequest> products;
}
