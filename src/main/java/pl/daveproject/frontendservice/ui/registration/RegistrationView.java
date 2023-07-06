package pl.daveproject.frontendservice.ui.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.applicationUser.model.ApplicationUser;
import pl.daveproject.frontendservice.ui.component.WebdietFormWrapper;
import pl.daveproject.frontendservice.ui.component.WebdietNotification;
import pl.daveproject.frontendservice.ui.component.type.WebdietNotificationType;
import pl.daveproject.frontendservice.ui.layout.BeforeLoginAppLayout;
import pl.daveproject.frontendservice.ui.login.LoginView;

@Slf4j
@Route(value = "/register", layout = BeforeLoginAppLayout.class)
public class RegistrationView extends VerticalLayout implements HasDynamicTitle {

    private final WebdietFormWrapper registrationForm;
    private final ApplicationUserService applicationUserService;

    public RegistrationView(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
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
            try {
                log.info("Registering new User: {}", e.getApplicationUser().getEmail());
                applicationUserService.registerUser(e.getApplicationUser());
                UI.getCurrent().navigate(LoginView.class);
            } catch (WebClientResponseException ex) {
                if (ex.getStatusCode() == HttpStatusCode.valueOf(409)) {
                    WebdietNotification.show(getTranslation("register-page.email-already-exists-error-message"),
                            WebdietNotificationType.ERROR);
                } else {
                    WebdietNotification.show(getTranslation("register-page.common-error-message"),
                            WebdietNotificationType.ERROR);
                }
            }
        });
    }

    private void redirectToLoginPageOnClose() {
        var form = (RegistrationForm) registrationForm.getFormLayout();
        form.addCloseListener(e -> UI.getCurrent().navigate(LoginView.class));
    }
}
