package pl.daveproject.frontendservice.registration;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.daveproject.frontendservice.applicationLayout.BeforeLoginAppLayout;
import pl.daveproject.frontendservice.registration.model.ActivityLevel;
import pl.daveproject.frontendservice.registration.model.ApplicationUser;
import pl.daveproject.frontendservice.registration.model.Gender;
import pl.daveproject.frontendservice.uiComponents.WebdietFormLayoutWrapper;
import pl.daveproject.frontendservice.uiComponents.WebdietUpload;

@Route(value = "/register", layout = BeforeLoginAppLayout.class)
public class RegistrationView extends VerticalLayout implements HasDynamicTitle {

    private final Binder<ApplicationUser> binder;
    private final EmailField emailField;
    private final PasswordField passwordField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final NumberField weightField;
    private final NumberField heightField;
    private final IntegerField ageField;
    private final ComboBox<Gender> genderField;
    private final ComboBox<ActivityLevel> activityLevelField;
    private final WebdietUpload photoField;

    public RegistrationView() {
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        this.binder = new Binder<>();
        this.binder.setBean(ApplicationUser.builder().build());
        this.emailField = new EmailField(getTranslation("register-page.email"));
        this.passwordField = new PasswordField(getTranslation("register-page.password"));
        this.firstNameField = new TextField(getTranslation("register-page.first-name"));
        this.lastNameField = new TextField(getTranslation("register-page.last-name"));
        this.weightField = new NumberField(getTranslation("register-page.weight"));
        this.heightField = new NumberField(getTranslation("register-page.height"));
        this.ageField = new IntegerField(getTranslation("register-page.age"));
        this.genderField = new ComboBox<>(getTranslation("register-page.gender"));
        this.genderField.setItems(Gender.values());
        this.activityLevelField = new ComboBox<>(getTranslation("register-page.activity-level"));
        this.activityLevelField.setItems(ActivityLevel.values());
        this.photoField = new WebdietUpload();
        setComboBoxValues();
        add(createFormLayout());
    }

    private void setComboBoxValues() {
        activityLevelField.setItems(ActivityLevel.values());
        activityLevelField.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));

        genderField.setItems(Gender.values());
        genderField.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));
    }

    private WebdietFormLayoutWrapper createFormLayout() {
        var form = new WebdietFormLayoutWrapper("register-page.sign-up");
        form.getFormLayout().add(emailField, 2);
        form.getFormLayout().add(passwordField,
                createConfirmPasswordField(),
                firstNameField,
                lastNameField,
                weightField,
                heightField,
                ageField,
                genderField,
                activityLevelField);
        photoField.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        form.getFormLayout().add(photoField, 2);
        return form;
    }

    private PasswordField createConfirmPasswordField() {
        return new PasswordField(getTranslation("register-page.confirm-password"));
    }

    @Override
    public String getPageTitle() {
        return getTranslation("register-page.title");
    }
}
