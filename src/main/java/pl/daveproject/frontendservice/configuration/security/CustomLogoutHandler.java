package pl.daveproject.frontendservice.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private static final String AUTH_SERVER_LOGOUT_ENDPOINT = "/logout";

    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String authServerBaseUri;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        var restTemplate = new RestTemplate();
        var authServerResponse = restTemplate.getForEntity(authServerBaseUri + AUTH_SERVER_LOGOUT_ENDPOINT, Void.class);
        if (authServerResponse.getStatusCode() == HttpStatus.OK) {
            log.info("Successfully logout from the system {}", authentication.getName());
        }
    }
}
