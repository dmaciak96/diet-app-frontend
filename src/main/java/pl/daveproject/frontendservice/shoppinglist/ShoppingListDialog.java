package pl.daveproject.frontendservice.shoppinglist;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;
import pl.daveproject.frontendservice.recipe.RecipeDataProvider;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingListDialogModel;
import pl.daveproject.frontendservice.shoppinglist.service.ShoppingListService;


@Slf4j
public class ShoppingListDialog extends CloseableDialog implements Translator {

  private final WebdietFormWrapper shoppingListForm;
  private final ShoppingListService shoppingListService;

  public ShoppingListDialog(ShoppingListService shoppingListService,
      ShoppingListDialogModel shoppingListDialogModel, RecipeDataProvider recipeDataProvider) {
    super("shopping-lists-form.title");
    this.shoppingListForm = new WebdietFormWrapper(
        new ShoppingListForm(shoppingListDialogModel, recipeDataProvider, shoppingListService),
        false);
    this.shoppingListService = shoppingListService;
    add(shoppingListForm);
    saveShoppingListOnSave();
  }

  private void saveShoppingListOnSave() {
    var form = (ShoppingListForm) shoppingListForm.getFormLayout();
    form.addSaveListener(e -> {
      log.info("Creating/Updating shopping list: {}", e.getShoppingList().getName());
      var createdShoppingList = shoppingListService.save(e.getShoppingList());
      log.info("Shopping list {} successfully created/updated", createdShoppingList.getName());
      this.close();
    });
  }
}
