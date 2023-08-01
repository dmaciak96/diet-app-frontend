package pl.daveproject.frontendservice.configuration.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends VaadinWebSecurity {

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auth -> auth.requestMatchers("/images/*.png", "/", "/login**", "/callback/", "/webjars/**",
                                "/error**").permitAll())
                .oauth2Login(login -> login.defaultSuccessUrl("/dashboard"))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(auth2 -> auth2.jwt(
                        token -> token.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/"));
        super.configure(http);
    }
}
