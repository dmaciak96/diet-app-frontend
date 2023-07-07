package pl.daveproject.frontendservice.product;

import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.grid.GridDataFilter;
import pl.daveproject.frontendservice.product.model.Product;

@Setter
@Component
public class ProductFilter implements Translator, GridDataFilter {
    private String searchValue;

    public boolean match(Product product) {
        var matchesProductName = matches(product.getName(), searchValue);
        var matchesProductType = matches(getTranslation(product.getType().getTranslationKey()), searchValue);
        return matchesProductName || matchesProductType;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
