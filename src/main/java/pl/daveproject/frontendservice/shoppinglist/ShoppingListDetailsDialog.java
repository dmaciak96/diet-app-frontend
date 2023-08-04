package pl.daveproject.frontendservice.shoppinglist;

import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingList;

public class ShoppingListDetailsDialog extends CloseableDialog implements Translator {

  public ShoppingListDetailsDialog(ShoppingList shoppingList) {
    super(shoppingList.getName(), false);
  }
}
