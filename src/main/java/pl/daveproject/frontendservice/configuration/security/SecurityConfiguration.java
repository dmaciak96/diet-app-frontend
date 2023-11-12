package pl.daveproject.frontendservice.configuration.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends VaadinWebSecurity {
    public static final String DEFAULT_SUCCESS_URL = "/dashboard";

    @Value("${authorization-endpoint}")
    private String authorizationEndpoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2Login ->
                        oauth2Login.loginPage(authorizationEndpoint)
                                .defaultSuccessUrl(DEFAULT_SUCCESS_URL))
                .oauth2Client(Customizer.withDefaults());
        super.configure(http);
    }
}
