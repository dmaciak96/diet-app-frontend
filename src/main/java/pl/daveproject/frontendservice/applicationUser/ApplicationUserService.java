package pl.daveproject.frontendservice.applicationUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.applicationUser.model.ApplicationUser;
import pl.daveproject.frontendservice.exception.UserNotLoginException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserService {
    private static final String REGISTER_ENDPOINT = "/applicationUsers";

    private final WebClient webClient;

    public DefaultOidcUser getCurrentUser() {
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            return (DefaultOidcUser) authentication.getPrincipal();
        }
        throw new UserNotLoginException();
    }

    public String getCurrentToken() {
        return getCurrentUser().getIdToken().getTokenValue();
    }

    public ApplicationUser registerUser(ApplicationUser applicationUser) {
        return webClient.post()
                .uri(REGISTER_ENDPOINT)
                .body(Mono.just(applicationUser), ApplicationUser.class)
                .retrieve()
                .bodyToMono(ApplicationUser.class)
                .block();
    }
}
