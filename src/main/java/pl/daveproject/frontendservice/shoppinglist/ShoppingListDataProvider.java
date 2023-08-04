package pl.daveproject.frontendservice.shoppinglist;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingList;
import pl.daveproject.frontendservice.shoppinglist.service.ShoppingListService;

@Component
@RequiredArgsConstructor
public class ShoppingListDataProvider extends
    AbstractBackEndDataProvider<ShoppingList, ShoppingListFilter> implements Translator {

  public static final String NAME_SORTING_KEY = "name";
  private final ShoppingListService shoppingListService;

  @Override
  protected Stream<ShoppingList> fetchFromBackEnd(Query<ShoppingList, ShoppingListFilter> query) {
    var stream = shoppingListService.findAll().stream();
    if (query.getFilter().isPresent()) {
      stream = stream.filter(recipe -> query.getFilter().get().match(recipe));
    }

    if (query.getSortOrders().size() > 0) {
      stream = stream.sorted(sortComparator(query.getSortOrders()));
    }
    return stream.skip(query.getOffset()).limit(query.getLimit());
  }

  @Override
  protected int sizeInBackEnd(Query<ShoppingList, ShoppingListFilter> query) {
    return (int) fetchFromBackEnd(query).count();
  }

  private Comparator<ShoppingList> sortComparator(List<QuerySortOrder> sortOrders) {
    return sortOrders.stream()
        .map(sortOrder -> {
          var comparator = shoppingListFieldComparator(sortOrder.getSorted());
          if (sortOrder.getDirection() == SortDirection.DESCENDING) {
            comparator = comparator.reversed();
          }
          return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
  }

  private Comparator<ShoppingList> shoppingListFieldComparator(String sorted) {
    return switch (sorted) {
      case NAME_SORTING_KEY -> Comparator.comparing(ShoppingList::getName);
      default -> (p1, p2) -> 0;
    };
  }
}
