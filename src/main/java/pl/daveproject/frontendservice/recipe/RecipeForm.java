package pl.daveproject.frontendservice.recipe;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.model.RecipeType;

@Uses(TextArea.class)
public class RecipeForm extends FormLayout {
  private final Binder<Recipe> binder;
  private final TextField name;
  private final ComboBox<RecipeType> type;
  private final TextArea description;

  public RecipeForm(Recipe recipe) {
    this.name = new TextField(getTranslation("recipe-form.name-label"));
    this.type = new ComboBox<>(getTranslation("recipe-form.type-label"));
    this.description = new TextArea(getTranslation("recipe-form-description"));
    this.type.setItems(RecipeType.values());
    this.type.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));

    this.binder = new BeanValidationBinder<>(Recipe.class);
    this.binder.setBean(recipe);
    this.binder.bindInstanceFields(this);

    add(name, type);
    add(description, 2);
    createSubmitButtons();
  }

  private void createSubmitButtons() {
    var save = new Button(getTranslation("form.save"));
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickShortcut(Key.ENTER);
    save.addClickListener(event -> validateAndSave());

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    var buttonLayout = new HorizontalLayout(save);
    buttonLayout.addClassNames(LumoUtility.Margin.Top.MEDIUM);
    add(buttonLayout);
  }

  private void validateAndSave() {
    if (binder.isValid()) {
      fireEvent(new RecipeForm.SaveEvent(this, binder.getBean()));
    }
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
