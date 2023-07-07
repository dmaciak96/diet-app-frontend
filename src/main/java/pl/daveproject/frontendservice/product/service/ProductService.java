package pl.daveproject.frontendservice.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.BaseRestService;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.model.ProductResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService extends BaseRestService {

    private static final String PRODUCTS_ENDPOINT = "/products";
    private static final String PRODUCTS_ENDPOINT_WITH_ID = "/products/{productId}";
    private static final String GET_PRODUCTS_ENDPOINT = "/products/search/findAllByApplicationUserId";
    private static final String APPLICATION_USER_ID_QUERY_PARAM = "applicationUserId";

    private final WebClient webClient;
    private final ApplicationUserService applicationUserService;
    private final ObjectMapper objectMapper;

    public List<Product> findAll() {
        var token = getJwtToken();
        var currentUser = applicationUserService.findCurrentUser();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_PRODUCTS_ENDPOINT)
                        .queryParam(APPLICATION_USER_ID_QUERY_PARAM, currentUser.getId())
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .map(response -> response.getEmbedded().getProducts())
                .block();
    }

    public Product saveOrUpdate(Product product) {
        if (product.getId() == null) {
            return save(product);
        } else {
            return update(product);
        }
    }

    private Product save(Product product) {
        var token = getJwtToken();
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(PRODUCTS_ENDPOINT)
                        .build())
                .body(Mono.just(product), Product.class)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    private Product update(Product product) {
        var token = getJwtToken();
        return webClient.put()
                .uri(PRODUCTS_ENDPOINT_WITH_ID, product.getId())
                .body(Mono.just(product), Product.class)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public void delete(UUID id) {
        var token = getJwtToken();
        webClient.delete()
                .uri(PRODUCTS_ENDPOINT_WITH_ID, id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
