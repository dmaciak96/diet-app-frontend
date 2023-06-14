package pl.daveproject.frontendservice.login;


import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;
import pl.daveproject.frontendservice.login.model.LoginRequest;
import pl.daveproject.frontendservice.login.service.LoginService;

@Slf4j
@Route(value = "/login")
@JsModule("prefers-color-scheme.js")
public class LoginView extends VerticalLayout {

    private final LoginService loginService;
    private LoginOverlay loginOverlay;
    private final LoginI18n i18n;

    public LoginView(LoginService loginService) {
        this.loginService = loginService;
        this.i18n = LoginI18n.createDefault();
        loginOverlay = new LoginOverlay();
        createLoginOverlay();
    }

    public void createLoginOverlay() {
        loginOverlay = new LoginOverlay();
        loginOverlay.setI18n(createLoginTranslationComponent());

        loginOverlay.setOpened(true);
        loginOverlay.addLoginListener(this::processLoginRequest);
        loginOverlay.addForgotPasswordListener(this::routeToForgotPasswordPage);
        add(loginOverlay);
    }

    private LoginI18n createLoginTranslationComponent() {
        var i18nHeader = new LoginI18n.Header();
        i18nHeader.setTitle(getTranslation("application.name"));
        i18nHeader.setDescription(getTranslation("application.description"));
        i18n.setHeader(i18nHeader);

        var i18nForm = i18n.getForm();
        i18nForm.setTitle(getTranslation("login-page.login"));
        i18nForm.setUsername(getTranslation("login-page.username"));
        i18nForm.setPassword(getTranslation("login-page.password"));
        i18nForm.setSubmit(getTranslation("login-page.login"));
        i18nForm.setForgotPassword(getTranslation("login-page.forgot-password"));
        i18n.setForm(i18nForm);

        var i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle(getTranslation("login-page.error-title"));
        i18nErrorMessage.setMessage(getTranslation("login-page.error-message"));
        i18n.setErrorMessage(i18nErrorMessage);

        return i18n;
    }

    private void routeToForgotPasswordPage(AbstractLogin.ForgotPasswordEvent event) {
        log.debug("Route to forgot password page...");
    }

    private void processLoginRequest(AbstractLogin.LoginEvent event) {
        log.debug("Login to system...");
        try {
            var response = loginService.sendLoginRequest(new LoginRequest(event.getUsername(), event.getPassword()));
            loginService.saveJwtToken(response);
        } catch (WebClientException e) {
            log.error("Login error: {}", e.getMessage());
            if (!e.getMessage().contains(HttpStatus.UNAUTHORIZED.toString())) {
                i18n.getErrorMessage().setMessage(getTranslation("login-page.error-message-fatal"));
            }
            loginOverlay.setError(true);
        }
    }
}
