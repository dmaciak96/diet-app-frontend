package pl.daveproject.frontendservice.caloricneeds;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.caloricneeds.model.TotalCaloricNeeds;
import pl.daveproject.frontendservice.caloricneeds.service.TotalCaloricNeedsService;
import pl.daveproject.frontendservice.component.Translator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TotalCaloricNeedsDataProvider extends
        AbstractBackEndDataProvider<TotalCaloricNeeds, TotalCaloricNeedsFilter> implements Translator {
    public static final String ADDED_DATE_SORTING_KEY = "addedDate";
    public static final String WEIGHT_SORTING_KEY = "weight";
    public static final String HEIGHT_SORTING_KEY = "height";
    public static final String AGE_SORTING_KEY = "age";
    public static final String ACTIVITY_LEVEL_SORTING_KEY = "activity-level";
    public static final String VALUE_SORTING_KEY = "value";

    private final TotalCaloricNeedsService totalCaloricNeedsService;

    @Override
    protected Stream<TotalCaloricNeeds> fetchFromBackEnd(Query<TotalCaloricNeeds, TotalCaloricNeedsFilter> query) {
        var stream = totalCaloricNeedsService.findAll().stream();
        if (query.getFilter().isPresent()) {
            stream = stream.filter(totalCaloricNeeds -> query.getFilter().get().match(totalCaloricNeeds));
        }

        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<TotalCaloricNeeds, TotalCaloricNeedsFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private Comparator<TotalCaloricNeeds> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream()
                .map(sortOrder -> {
                    var comparator = totalCaloricNeedsFieldComparator(sortOrder.getSorted());
                    if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                        comparator = comparator.reversed();
                    }
                    return comparator;
                }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private Comparator<TotalCaloricNeeds> totalCaloricNeedsFieldComparator(String sorted) {
        return switch (sorted) {
            case WEIGHT_SORTING_KEY -> Comparator.comparing(TotalCaloricNeeds::getWeight);
            case HEIGHT_SORTING_KEY -> Comparator.comparing(TotalCaloricNeeds::getHeight);
            case VALUE_SORTING_KEY -> Comparator.comparing(TotalCaloricNeeds::getValue);
            case ADDED_DATE_SORTING_KEY -> Comparator.comparing(TotalCaloricNeeds::getAddedDate);
            case AGE_SORTING_KEY -> Comparator.comparing(TotalCaloricNeeds::getAge);
            case ACTIVITY_LEVEL_SORTING_KEY ->
                    Comparator.comparing(totalCaloricNeeds -> getTranslation(totalCaloricNeeds.getActivityLevel().getTranslationKey()));
            default -> (p1, p2) -> 0;
        };
    }
}
