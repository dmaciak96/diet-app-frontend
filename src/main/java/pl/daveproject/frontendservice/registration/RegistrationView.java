package pl.daveproject.frontendservice.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.applicationLayout.BeforeLoginAppLayout;
import pl.daveproject.frontendservice.login.LoginView;
import pl.daveproject.frontendservice.registration.model.ApplicationUser;
import pl.daveproject.frontendservice.uiComponents.WebdietFormWrapper;

@Slf4j
@Route(value = "/register", layout = BeforeLoginAppLayout.class)
public class RegistrationView extends VerticalLayout implements HasDynamicTitle {

    private final WebdietFormWrapper registrationForm;
    private final RegistrationService registrationService;

    public RegistrationView(RegistrationService registrationService) {
        this.registrationService = registrationService;
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        this.registrationForm = new WebdietFormWrapper("register-page.sign-up",
                new RegistrationForm(ApplicationUser.builder().build()));
        add(registrationForm);
        registerUserOnSave();
        redirectToLoginPageOnClose();
    }

    @Override
    public String getPageTitle() {
        return getTranslation("register-page.title");
    }

    private void registerUserOnSave() {
        var form = (RegistrationForm) registrationForm.getFormLayout();
        form.addSaveListener(e -> {
            log.info("Registering new User: {}", e.getApplicationUser().getEmail());
            registrationService.registerUser(e.getApplicationUser());
            UI.getCurrent().navigate(LoginView.class);
        });
    }

    private void redirectToLoginPageOnClose() {
        var form = (RegistrationForm) registrationForm.getFormLayout();
        form.addCloseListener(e -> UI.getCurrent().navigate(LoginView.class));
    }
}
