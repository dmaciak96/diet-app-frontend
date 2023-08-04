package pl.daveproject.frontendservice.shoppinglist;

import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.grid.GridDataFilter;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingList;

@Setter
@Component
public class ShoppingListFilter implements Translator, GridDataFilter {

  private String searchValue;

  public boolean match(ShoppingList shoppingList) {
    return matches(shoppingList.getName(), searchValue);
  }

  private boolean matches(String value, String searchTerm) {
    return searchTerm == null || searchTerm.isEmpty()
        || value.toLowerCase().contains(searchTerm.toLowerCase());
  }

}
