package pl.daveproject.frontendservice.ui.login;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;
import pl.daveproject.frontendservice.ui.dashboard.DashboardView;
import pl.daveproject.frontendservice.ui.layout.BeforeLoginAppLayout;
import pl.daveproject.frontendservice.ui.layout.EmptyView;
import pl.daveproject.frontendservice.ui.login.model.LoginRequest;
import pl.daveproject.frontendservice.ui.login.service.LoginService;

@Slf4j
@Route(value = "/login", layout = BeforeLoginAppLayout.class)
public class LoginView extends VerticalLayout implements HasDynamicTitle {

    private final LoginService loginService;
    private final LoginForm loginForm;
    private final LoginI18n i18n;

    public LoginView(LoginService loginService) {
        this.loginService = loginService;
        this.i18n = LoginI18n.createDefault();
        this.loginForm = new LoginForm();
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        setupLoginForm();
    }

    public void setupLoginForm() {
        loginForm.setI18n(createLoginTranslationComponent());
        loginForm.addLoginListener(this::processLoginRequest);
        loginForm.addForgotPasswordListener(this::routeToForgotPasswordPage);
        add(loginForm);
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
        //TODO: Implement forgot password functionallity
        UI.getCurrent().navigate(EmptyView.class);
    }

    private void processLoginRequest(AbstractLogin.LoginEvent event) {
        log.debug("Login to system...");
        try {
            var response = loginService.sendLoginRequest(new LoginRequest(event.getUsername(), event.getPassword()));
            loginService.saveJwtToken(response);
            UI.getCurrent().navigate(DashboardView.class);
        } catch (WebClientException e) {
            log.error("Login error: {}", e.getMessage());
            if (!e.getMessage().contains(HttpStatus.UNAUTHORIZED.toString())) {
                i18n.getErrorMessage().setMessage(getTranslation("login-page.error-message-fatal"));
            }
            loginForm.setError(true);
        }
    }

    @Override
    public String getPageTitle() {
        return getTranslation("login-page.title");
    }
}
