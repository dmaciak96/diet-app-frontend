package pl.daveproject.frontendservice.caloricneeds;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.caloricneeds.model.TotalCaloricNeeds;
import pl.daveproject.frontendservice.caloricneeds.service.TotalCaloricNeedsService;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;

@Slf4j
public class TotalCaloricNeedsDialog extends CloseableDialog implements Translator {

    private final WebdietFormWrapper totalCaloricNeedsForm;
    private final TotalCaloricNeedsService totalCaloricNeedsService;

    public TotalCaloricNeedsDialog(TotalCaloricNeedsService totalCaloricNeedsService, TotalCaloricNeeds totalCaloricNeeds) {
        super("total-caloric-needs.window-title");
        this.totalCaloricNeedsService = totalCaloricNeedsService;
        this.totalCaloricNeedsForm = new WebdietFormWrapper(new TotalCaloricNeedsForm(totalCaloricNeeds), false);
        add(totalCaloricNeedsForm);
        saveTotalCaloricNeedsOnSave();
    }

    private void saveTotalCaloricNeedsOnSave() {
        var form = (TotalCaloricNeedsForm) totalCaloricNeedsForm.getFormLayout();
        form.addSaveListener(e -> {
            log.info("Creating total caloric needs: {}", e.getTotalCaloricNeeds().getId());
            var createdBmi = totalCaloricNeedsService.save(e.getTotalCaloricNeeds());
            log.info("Total caloric needs {} successfully created", createdBmi.getId());
            this.close();
        });
    }
}
