package pl.daveproject.frontendservice.caloricneeds;

import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.caloricneeds.model.TotalCaloricNeeds;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.grid.GridDataFilter;

@Setter
@Component
public class TotalCaloricNeedsFilter implements Translator, GridDataFilter {

    private String searchValue;

    public boolean match(TotalCaloricNeeds totalCaloricNeeds) {
        return matches(getTranslation(totalCaloricNeeds.getActivityLevel().getTranslationKey()), searchValue);
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
