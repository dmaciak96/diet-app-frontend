package pl.daveproject.frontendservice.registration;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import pl.daveproject.frontendservice.registration.model.ActivityLevel;
import pl.daveproject.frontendservice.registration.model.ApplicationUser;
import pl.daveproject.frontendservice.registration.model.Gender;
import pl.daveproject.frontendservice.uiComponents.WebdietUpload;

public class RegistrationForm extends FormLayout {

    private final Binder<ApplicationUser> binder;
    private final EmailField email;
    private final PasswordField password;
    private final TextField firstName;
    private final TextField lastName;
    private final NumberField weight;
    private final NumberField height;
    private final IntegerField age;
    private final ComboBox<Gender> gender;
    private final ComboBox<ActivityLevel> activityLevel;
    private final WebdietUpload photo;

    public RegistrationForm(ApplicationUser applicationUser) {
        this.email = new EmailField(getTranslation("register-page.email"));
        this.password = new PasswordField(getTranslation("register-page.password"));
        this.firstName = new TextField(getTranslation("register-page.first-name"));
        this.lastName = new TextField(getTranslation("register-page.last-name"));
        this.weight = new NumberField(getTranslation("register-page.weight"));
        this.height = new NumberField(getTranslation("register-page.height"));
        this.age = new IntegerField(getTranslation("register-page.age"));
        this.gender = new ComboBox<>(getTranslation("register-page.gender"));
        this.gender.setItems(Gender.values());
        this.activityLevel = new ComboBox<>(getTranslation("register-page.activity-level"));
        this.activityLevel.setItems(ActivityLevel.values());
        this.photo = new WebdietUpload();

        this.binder = new BeanValidationBinder<>(ApplicationUser.class);
        this.binder.setBean(applicationUser);
        this.binder.bindInstanceFields(this);
        this.binder.setBean(ApplicationUser.builder().build());

        setComboBoxValues();
        addElementsToForm();
        createSubmitButtons();
    }

    private void setComboBoxValues() {
        activityLevel.setItems(ActivityLevel.values());
        activityLevel.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));

        gender.setItems(Gender.values());
        gender.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));
    }

    private void addElementsToForm() {
        this.add(email, 2);
        this.add(password,
                createConfirmPasswordField(),
                firstName,
                lastName,
                weight,
                height,
                age,
                gender,
                activityLevel);
        photo.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        this.add(photo, 2);
    }

    private PasswordField createConfirmPasswordField() {
        return new PasswordField(getTranslation("register-page.confirm-password"));
    }

    private void createSubmitButtons() {
        var save = new Button(getTranslation("register-page.sign-up"));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        var close = new Button(getTranslation("form.close"));
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        close.addClickShortcut(Key.ESCAPE);
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        var buttonLayout = new HorizontalLayout(save, close);
        buttonLayout.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        add(buttonLayout);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    @Getter
    public static abstract class RegistrationFormEvent extends ComponentEvent<RegistrationForm> {

        private ApplicationUser applicationUser;

        protected RegistrationFormEvent(RegistrationForm source, ApplicationUser applicationUser) {
            super(source, false);
            this.applicationUser = applicationUser;
        }
    }

    public static class SaveEvent extends RegistrationFormEvent {
        SaveEvent(RegistrationForm source, ApplicationUser applicationUser) {
            super(source, applicationUser);
        }
    }

    public static class CloseEvent extends RegistrationFormEvent {
        CloseEvent(RegistrationForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
