package pl.daveproject.frontendservice.configuration.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends VaadinWebSecurity {
    public static final String LOGIN_SUCCESS_URL = "/dashboard";
    public static final String LOGOUT_SUCCESS_URL = "/";
    public static final String LOGOUT_ENDPOINT = "/logout";
    public static final String SESSION_ID_COOKIE = "JSESSIONID";

    @Value("${authorization-endpoint}")
    private String authorizationEndpoint;

    private final CustomLogoutHandler customLogoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2Login ->
                        oauth2Login.loginPage(authorizationEndpoint)
                                .defaultSuccessUrl(LOGIN_SUCCESS_URL))
                .logout(logout -> logout.addLogoutHandler(customLogoutHandler)
                        .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENDPOINT))
                        .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies(SESSION_ID_COOKIE))
                .oauth2Client(Customizer.withDefaults());
        super.configure(http);
    }
}
