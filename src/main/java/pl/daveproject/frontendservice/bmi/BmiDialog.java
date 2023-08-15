package pl.daveproject.frontendservice.bmi;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.bmi.model.Bmi;
import pl.daveproject.frontendservice.bmi.service.BmiService;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;

@Slf4j
public class BmiDialog extends CloseableDialog implements Translator {

    private final WebdietFormWrapper bmiForm;
    private final BmiService bmiService;

    public BmiDialog(BmiService bmiService, Bmi bmi) {
        super("bmi-view.window-title");
        this.bmiService = bmiService;
        this.bmiForm = new WebdietFormWrapper(new BmiForm(bmi), false);
        add(bmiForm);
        saveBmiOnSave();
    }

    private void saveBmiOnSave() {
        var form = (BmiForm) bmiForm.getFormLayout();
        form.addSaveListener(e -> {
            log.info("Creating BMI: {}", e.getBmi().getId());
            var createdBmi = bmiService.save(e.getBmi());
            log.info("BMI {} successfully created", createdBmi.getId());
            this.close();
        });
    }
}
