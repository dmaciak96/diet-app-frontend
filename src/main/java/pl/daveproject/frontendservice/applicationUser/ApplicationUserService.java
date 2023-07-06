package pl.daveproject.frontendservice.applicationUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.BaseRestService;
import pl.daveproject.frontendservice.applicationUser.model.ApplicationUser;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserService extends BaseRestService {
    private static final String EMAIL_JSON_KEY = "sub";
    private static final String EMAIL_QUERY_PARAM_NAME = "email";
    private static final String GET_USER_BY_EMAIL_ENDPOINT = "/applicationUsers/search/findByEmail";
    private static final String REGISTER_ENDPOINT = "/applicationUsers";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ApplicationUser findCurrentUser() throws JsonProcessingException {
        var token = getJwtToken();
        var email = getEmailFromToken(token);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_USER_BY_EMAIL_ENDPOINT)
                        .queryParam(EMAIL_QUERY_PARAM_NAME, email)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(ApplicationUser.class)
                .block();
    }

    private String getEmailFromToken(String token) throws JsonProcessingException {
        var jwtTokenSections = token.split("\\.");
        var payloadDecodedJson = new String(Base64.getUrlDecoder().decode(jwtTokenSections[1]));
        var jsonObject = objectMapper.readValue(payloadDecodedJson, ObjectNode.class);
        return jsonObject.get(EMAIL_JSON_KEY).asText();
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
