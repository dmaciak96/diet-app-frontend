package pl.daveproject.frontendservice.recipe;

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
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.service.RecipeService;

@Component
@RequiredArgsConstructor
public class RecipeDataProvider extends
    AbstractBackEndDataProvider<Recipe, RecipeFilter> implements Translator {

  public static final String NAME_SORTING_KEY = "name";
  public static final String TYPE_SORTING_KEY = "type";
  public static final String KCAL_SORTING_KEY = "kcal";

  private final RecipeService recipeService;


  @Override
  protected Stream<Recipe> fetchFromBackEnd(Query<Recipe, RecipeFilter> query) {
    var recipeStream = recipeService.findAll().stream();
    if (query.getFilter().isPresent()) {
      recipeStream = recipeStream.filter(recipe -> query.getFilter().get().match(recipe));
    }

    if (query.getSortOrders().size() > 0) {
      recipeStream = recipeStream.sorted(sortComparator(query.getSortOrders()));
    }
    return recipeStream.skip(query.getOffset()).limit(query.getLimit());
  }

  @Override
  protected int sizeInBackEnd(Query<Recipe, RecipeFilter> query) {
    return (int) fetchFromBackEnd(query).count();
  }

  private Comparator<Recipe> sortComparator(List<QuerySortOrder> sortOrders) {
    return sortOrders.stream()
        .map(sortOrder -> {
          var comparator = recipeFieldComparator(sortOrder.getSorted());
          if (sortOrder.getDirection() == SortDirection.DESCENDING) {
            comparator = comparator.reversed();
          }
          return comparator;
        }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
  }

  private Comparator<Recipe> recipeFieldComparator(String sorted) {
    return switch (sorted) {
      case NAME_SORTING_KEY -> Comparator.comparing(Recipe::getName);
      case TYPE_SORTING_KEY ->
          Comparator.comparing(recipe -> getTranslation(recipe.getType().getTranslationKey()));
      case KCAL_SORTING_KEY -> Comparator.comparing(Recipe::getKcal);
      default -> (p1, p2) -> 0;
    };
  }
}
