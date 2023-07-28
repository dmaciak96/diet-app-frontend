package pl.daveproject.frontendservice.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.daveproject.frontendservice.StartView;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.applicationUser.model.ApplicationUser;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;
import pl.daveproject.frontendservice.component.WebdietNotification;
import pl.daveproject.frontendservice.component.type.WebdietNotificationType;
import pl.daveproject.frontendservice.layout.BeforeLoginAppLayout;

@Slf4j
@AnonymousAllowed
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
        redirectToStartPageOnClose();
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
                UI.getCurrent().navigate(StartView.class);
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

    private void redirectToStartPageOnClose() {
        var form = (RegistrationForm) registrationForm.getFormLayout();
        form.addCloseListener(e -> UI.getCurrent().navigate(StartView.class));
    }
}
