package pl.daveproject.frontendservice.bmi;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.bmi.model.Bmi;
import pl.daveproject.frontendservice.bmi.service.BmiService;
import pl.daveproject.frontendservice.component.Translator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BmiDataProvider extends AbstractBackEndDataProvider<Bmi, BmiFilter> implements Translator {

    public static final String WEIGHT_SORTING_KEY = "weight";
    public static final String HEIGHT_SORTING_KEY = "height";
    public static final String VALUE_SORTING_KEY = "value";
    public static final String ADDED_DATE_SORTING_KEY = "addedDate";
    public static final String RATE_SORTING_KEY = "rate";

    private final BmiService bmiService;

    @Override
    protected Stream<Bmi> fetchFromBackEnd(Query<Bmi, BmiFilter> query) {
        var stream = bmiService.findAll().stream();
        if (query.getFilter().isPresent()) {
            stream = stream.filter(recipe -> query.getFilter().get().match(recipe));
        }

        if (query.getSortOrders().size() > 0) {
            stream = stream.sorted(sortComparator(query.getSortOrders()));
        }
        return stream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Bmi, BmiFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private Comparator<Bmi> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream()
                .map(sortOrder -> {
                    var comparator = bmiFieldComparator(sortOrder.getSorted());
                    if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                        comparator = comparator.reversed();
                    }
                    return comparator;
                }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private Comparator<Bmi> bmiFieldComparator(String sorted) {
        return switch (sorted) {
            case WEIGHT_SORTING_KEY -> Comparator.comparing(Bmi::getWeight);
            case HEIGHT_SORTING_KEY -> Comparator.comparing(Bmi::getHeight);
            case VALUE_SORTING_KEY -> Comparator.comparing(Bmi::getValue);
            case ADDED_DATE_SORTING_KEY -> Comparator.comparing(Bmi::getAddedDate);
            case RATE_SORTING_KEY -> Comparator.comparing(Bmi::getRate);
            default -> (p1, p2) -> 0;
        };
    }
}
