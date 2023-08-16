package pl.daveproject.frontendservice.bmi;

import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.daveproject.frontendservice.bmi.model.Bmi;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.grid.GridDataFilter;

@Setter
@Component
public class BmiFilter implements Translator, GridDataFilter {

    private String searchValue;

    public boolean match(Bmi bmi) {
        return matches(getTranslation(bmi.getRate().getTranslationKey()), searchValue);
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
