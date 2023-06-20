package pl.daveproject.frontendservice.registration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.registration.model.ApplicationUser;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private static final String REGISTER_ENDPOINT = "/applicationUsers";

    private final WebClient webClient;

    public ApplicationUser registerUser(ApplicationUser applicationUser) {
        return webClient.post()
                .uri(REGISTER_ENDPOINT)
                .body(Mono.just(applicationUser), ApplicationUser.class)
                .retrieve()
                .bodyToMono(ApplicationUser.class)
                .block();
    }
}
