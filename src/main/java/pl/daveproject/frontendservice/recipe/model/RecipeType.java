package pl.daveproject.frontendservice.recipe.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecipeType {
  BREAKFAST("recipes-type.breakfast"),
  SECOND_BREAKFAST("recipes-type.second-breakfast"),
  LUNCH("recipes-type.lunch"),
  TEA("recipes-type.tea"),
  DINNER("recipes-type.dinner");

  private final String translationKey;
}
