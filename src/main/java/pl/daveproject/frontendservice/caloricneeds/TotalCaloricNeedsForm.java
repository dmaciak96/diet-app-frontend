package pl.daveproject.frontendservice.caloricneeds;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import pl.daveproject.frontendservice.bmi.model.UnitSystem;
import pl.daveproject.frontendservice.caloricneeds.model.ActivityLevel;
import pl.daveproject.frontendservice.caloricneeds.model.Gender;
import pl.daveproject.frontendservice.caloricneeds.model.TotalCaloricNeeds;

public class TotalCaloricNeedsForm extends FormLayout {

    private final Binder<TotalCaloricNeeds> binder;
    private final NumberField weight;
    private final NumberField height;
    private final ComboBox<UnitSystem> unit;
    private final ComboBox<Gender> gender;
    private final ComboBox<ActivityLevel> activityLevel;
    private final IntegerField age;

    public TotalCaloricNeedsForm(TotalCaloricNeeds totalCaloricNeeds) {
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
                this.height.setTooltipText(getTranslation("height.centimeters"));
                this.weight.setTooltipText(getTranslation("weight.kilograms"));
            }
        });

        this.gender = new ComboBox<>(getTranslation("gender"));
        this.gender.setItems(Gender.values());
        this.gender.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));

        this.activityLevel = new ComboBox<>(getTranslation("activity-level"));
        this.activityLevel.setItems(ActivityLevel.values());
        this.activityLevel.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));

        this.age = new IntegerField(getTranslation("total-caloric-needs.age"));
        this.age.setMin(0);
        this.age.setMax(120);

        this.binder = new BeanValidationBinder<>(TotalCaloricNeeds.class);
        this.binder.setBean(totalCaloricNeeds);
        this.binder.bindInstanceFields(this);

        this.add(gender, 2);
        this.add(unit, weight, height, age);
        this.add(activityLevel, 2);
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
            fireEvent(new TotalCaloricNeedsForm.SaveEvent(this, binder.getBean()));
        }
    }

    @Getter
    public static abstract class TotalCaloricNeedsFormEvent extends ComponentEvent<TotalCaloricNeedsForm> {

        private TotalCaloricNeeds totalCaloricNeeds;

        protected TotalCaloricNeedsFormEvent(TotalCaloricNeedsForm source, TotalCaloricNeeds totalCaloricNeeds) {
            super(source, false);
            this.totalCaloricNeeds = totalCaloricNeeds;
        }
    }

    public static class SaveEvent extends TotalCaloricNeedsForm.TotalCaloricNeedsFormEvent {
        SaveEvent(TotalCaloricNeedsForm source, TotalCaloricNeeds totalCaloricNeeds) {
            super(source, totalCaloricNeeds);
        }
    }

    public Registration addSaveListener(ComponentEventListener<TotalCaloricNeedsForm.SaveEvent> listener) {
        return addListener(TotalCaloricNeedsForm.SaveEvent.class, listener);
    }
}
