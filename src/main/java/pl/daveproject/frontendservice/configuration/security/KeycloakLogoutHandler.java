package pl.daveproject.frontendservice.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakLogoutHandler implements LogoutHandler {

  private static final String LOGOUT_ENDPOINT = "%s/protocol/openid-connect/logout";

  private final WebClient webClient;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    var user = (OidcUser) authentication;
    var builder = UriComponentsBuilder
        .fromUriString(LOGOUT_ENDPOINT.formatted(user.getIssuer()))
        .queryParam("id_token_hint", user.getIdToken().getTokenValue());

    webClient.get()
        .uri(builder.toUriString())
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
