package pl.daveproject.frontendservice.caloricneeds.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.caloricneeds.model.TotalCaloricNeeds;
import pl.daveproject.frontendservice.user.UserService;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TotalCaloricNeedsService {

    private static final String TOTAL_CALORIC_NEEDS_ENDPOINT = "/total-caloric-needs";
    private static final String TOTAL_CALORIC_NEEDS_ENDPOINT_WITH_ID = "/total-caloric-needs/{id}";

    private final WebClient webClient;
    private final UserService userService;

    public List<TotalCaloricNeeds> findAll() {
        var token = userService.getCurrentToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(TOTAL_CALORIC_NEEDS_ENDPOINT)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(TotalCaloricNeeds.class)
                .collectList()
                .block();
    }

    public List<TotalCaloricNeeds> findAllBetweenDates(LocalDate from, LocalDate to) {
        var token = userService.getCurrentToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(TOTAL_CALORIC_NEEDS_ENDPOINT)
                        .queryParam("from", from.format(DateTimeFormatter.ISO_DATE))
                        .queryParam("to", to.format(DateTimeFormatter.ISO_DATE))
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(TotalCaloricNeeds.class)
                .collectList()
                .block();
    }

    public TotalCaloricNeeds save(TotalCaloricNeeds totalCaloricNeeds) {
        var token = userService.getCurrentToken();
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(TOTAL_CALORIC_NEEDS_ENDPOINT)
                        .build())
                .body(Mono.just(totalCaloricNeeds), TotalCaloricNeeds.class)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(TotalCaloricNeeds.class)
                .block();
    }

    public void delete(UUID id) {
        var token = userService.getCurrentToken();
        webClient.delete()
                .uri(TOTAL_CALORIC_NEEDS_ENDPOINT_WITH_ID, id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(TotalCaloricNeeds.class)
                .block();
    }
}
