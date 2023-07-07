package pl.daveproject.frontendservice.product.service;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.product.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ProductDataProvider extends AbstractBackEndDataProvider<Product, ProductFilter> implements Translator {
    public static final String NAME_SORTING_KEY = "name";
    public static final String TYPE_SORTING_KEY = "type";
    public static final String KCAL_SORTING_KEY = "kcal";

    private final ProductService productService;

    @Override
    protected Stream<Product> fetchFromBackEnd(Query<Product, ProductFilter> query) {
        var productsStream = productService.findAll().stream();
        if (query.getFilter().isPresent()) {
            productsStream = productsStream.filter(person -> query.getFilter().get().match(person));
        }

        if (query.getSortOrders().size() > 0) {
            productsStream = productsStream.sorted(sortComparator(query.getSortOrders()));
        }
        return productsStream.skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<Product, ProductFilter> query) {
        return (int) fetchFromBackEnd(query).count();
    }

    private Comparator<Product> sortComparator(List<QuerySortOrder> sortOrders) {
        return sortOrders.stream()
                .map(sortOrder -> {
                    var comparator = productFieldComparator(sortOrder.getSorted());
                    if (sortOrder.getDirection() == SortDirection.DESCENDING) {
                        comparator = comparator.reversed();
                    }
                    return comparator;
                }).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
    }

    private Comparator<Product> productFieldComparator(String sorted) {
        return switch (sorted) {
            case NAME_SORTING_KEY -> Comparator.comparing(Product::getName);
            case TYPE_SORTING_KEY ->
                    Comparator.comparing(product -> getTranslation(product.getType().getTranslationKey()));
            case KCAL_SORTING_KEY -> Comparator.comparing(Product::getKcal);
            default -> (p1, p2) -> 0;
        };
    }
}
