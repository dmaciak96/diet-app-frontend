package pl.daveproject.frontendservice.configuration.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends VaadinWebSecurity {

    @Value("${authorization-endpoint}")
    private String authorizationEndpoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2Login ->
                        oauth2Login.loginPage(authorizationEndpoint))
                .oauth2Client(Customizer.withDefaults());
        super.configure(http);
    }
}
