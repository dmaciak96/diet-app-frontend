package pl.daveproject.frontendservice.ui.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.BaseRestService;
import pl.daveproject.frontendservice.ui.login.model.LoginRequest;
import pl.daveproject.frontendservice.ui.login.model.LoginResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService extends BaseRestService {
    private static final String LOGIN_ENDPOINT = "/login";

    private final WebClient webClient;

    public LoginResponse sendLoginRequest(LoginRequest loginRequest) {
        log.info("Sending login request for email: {}", loginRequest.email());
        return webClient.post()
                .uri(LOGIN_ENDPOINT)
                .body(Mono.just(loginRequest), LoginRequest.class)
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .block();
    }

    public void logout() {
        var cookie = getCookieByName(BaseRestService.JWT_COOKIE_NAME);
        if (cookie.isEmpty()) {
            log.info("JWT token cookie not found, nothing to do");
            return;
        }
        cookie.get().setMaxAge(0);
        log.info("Logout current user successfully");
    }
}
