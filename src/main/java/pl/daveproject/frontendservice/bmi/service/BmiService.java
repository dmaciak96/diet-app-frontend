package pl.daveproject.frontendservice.bmi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.bmi.model.Bmi;
import pl.daveproject.frontendservice.user.UserService;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BmiService {

    private static final String BMI_ENDPOINT = "/bmi";
    private static final String BMI_ENDPOINT_WITH_ID = "/bmi/{id}";

    private final WebClient webClient;
    private final UserService userService;

    public List<Bmi> findAll() {
        var token = userService.getCurrentToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BMI_ENDPOINT)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(Bmi.class)
                .collectList()
                .block();
    }

    public Optional<Bmi> findLatest() {
        return findAll().stream()
                .max(Comparator.comparing(Bmi::getAddedDate));
    }

    public List<Bmi> findAllBetweenDates(LocalDate from, LocalDate to) {
        var token = userService.getCurrentToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(BMI_ENDPOINT)
                        .queryParam("from", from.format(DateTimeFormatter.ISO_DATE))
                        .queryParam("to", to.format(DateTimeFormatter.ISO_DATE))
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(Bmi.class)
                .collectList()
                .block();
    }

    public Bmi save(Bmi Bmi) {
        var token = userService.getCurrentToken();
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(BMI_ENDPOINT)
                        .build())
                .body(Mono.just(Bmi), Bmi.class)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Bmi.class)
                .block();
    }

    public void delete(UUID id) {
        var token = userService.getCurrentToken();
        webClient.delete()
                .uri(BMI_ENDPOINT_WITH_ID, id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Bmi.class)
                .block();
    }
}
