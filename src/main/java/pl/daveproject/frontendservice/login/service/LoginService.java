package pl.daveproject.frontendservice.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.BaseRestService;
import pl.daveproject.frontendservice.login.model.LoginRequest;
import pl.daveproject.frontendservice.login.model.LoginResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginService extends BaseRestService {
    private static final String LOGIN_ENDPOINT = "/login";

    private final WebClient webClient;

    public LoginResponse sendLoginRequest(LoginRequest loginRequest) {
        return webClient.post()
                .uri(LOGIN_ENDPOINT)
                .body(Mono.just(loginRequest), LoginRequest.class)
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .block();
    }
}
