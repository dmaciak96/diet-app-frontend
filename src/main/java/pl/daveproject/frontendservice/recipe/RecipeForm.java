package pl.daveproject.frontendservice.recipe;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import pl.daveproject.frontendservice.recipe.model.Recipe;

public class RecipeForm extends FormLayout {

  public RecipeForm(Recipe recipe) {
  }

  @Getter
  public static abstract class RecipeFormEvent extends ComponentEvent<RecipeForm> {

    private Recipe recipe;

    protected RecipeFormEvent(RecipeForm source, Recipe recipe) {
      super(source, false);
      this.recipe = recipe;
    }
  }

  public static class SaveEvent extends RecipeForm.RecipeFormEvent {

    SaveEvent(RecipeForm source, Recipe recipe) {
      super(source, recipe);
    }
  }

  public Registration addSaveListener(ComponentEventListener<RecipeForm.SaveEvent> listener) {
    return addListener(RecipeForm.SaveEvent.class, listener);
  }
}
