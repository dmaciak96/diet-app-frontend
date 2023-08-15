package pl.daveproject.frontendservice.bmi.service;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import pl.daveproject.frontendservice.bmi.model.Bmi;
import pl.daveproject.frontendservice.bmi.model.UnitSystem;

@Uses(TextArea.class)
public class BmiForm extends FormLayout {

    private final Binder<Bmi> binder;
    private final ComboBox<UnitSystem> unit;
    private final NumberField weight;
    private final NumberField height;

    public BmiForm(Bmi bmi) {
        this.height = new NumberField(getTranslation("bmi-view.height"));
        this.weight = new NumberField(getTranslation("bmi-view.weight"));

        this.height.setTooltipText(getTranslation("height.meters"));
        this.height.setMin(0.0);
        this.weight.setTooltipText(getTranslation("weight.kilograms"));
        this.weight.setMin(0.0);

        this.unit = new ComboBox<>(getTranslation("bmi-view.unit"));
        this.unit.setItems(UnitSystem.values());
        this.unit.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));
        this.unit.addValueChangeListener(e -> {
            if (e.getValue() == UnitSystem.IMPERIAL) {
                this.height.setTooltipText(getTranslation("height.feet"));
                this.weight.setTooltipText(getTranslation("weight.pounds"));
            } else {
                this.height.setTooltipText(getTranslation("height.meters"));
                this.weight.setTooltipText(getTranslation("weight.kilograms"));
            }
        });

        this.binder = new BeanValidationBinder<>(Bmi.class);
        this.binder.setBean(bmi);
        this.binder.bindInstanceFields(this);

        this.add(unit, 2);
        this.add(weight, height);
        createSubmitButtons();
    }

    private void createSubmitButtons() {
        var save = new Button(getTranslation("form.save"));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        var buttonLayout = new HorizontalLayout(save);
        buttonLayout.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        add(buttonLayout);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new BmiForm.SaveEvent(this, binder.getBean()));
        }
    }

    @Getter
    public static abstract class BmiFormEvent extends ComponentEvent<BmiForm> {

        private Bmi bmi;

        protected BmiFormEvent(BmiForm source, Bmi bmi) {
            super(source, false);
            this.bmi = bmi;
        }
    }

    public static class SaveEvent extends BmiForm.BmiFormEvent {
        SaveEvent(BmiForm source, Bmi bmi) {
            super(source, bmi);
        }
    }

    public Registration addSaveListener(ComponentEventListener<BmiForm.SaveEvent> listener) {
        return addListener(BmiForm.SaveEvent.class, listener);
    }
}
