package pl.daveproject.frontendservice.login;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route("/login")
public class LoginView extends Div {
    public LoginView() {
        createLoginOverlay();
    }

    public void createLoginOverlay() {
        var loginOverlay = new LoginOverlay();
        loginOverlay.setI18n(createLoginTranslationComponent());

        loginOverlay.setOpened(true);
        loginOverlay.addLoginListener(this::processLoginRequest);
        loginOverlay.addForgotPasswordListener(this::routeToForgotPasswordPage);
        add(loginOverlay);
    }

    private LoginI18n createLoginTranslationComponent() {
        var i18n = LoginI18n.createDefault();

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
        log.info("Route to forgot password page...");
    }

    private void processLoginRequest(AbstractLogin.LoginEvent event) {
        log.info("Login to system...");
    }
}
