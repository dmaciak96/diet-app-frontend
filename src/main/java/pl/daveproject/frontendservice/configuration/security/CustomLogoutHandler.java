package pl.daveproject.frontendservice.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private static final String AUTH_SERVER_LOGOUT_ENDPOINT = "/logout";

    private final WebClient webClient;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        var builder = UriComponentsBuilder.fromUriString(AUTH_SERVER_LOGOUT_ENDPOINT);
        webClient.post()
                .uri(builder.toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
