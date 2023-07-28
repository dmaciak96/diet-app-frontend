package pl.daveproject.frontendservice.product.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.model.ProductResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private static final String PRODUCTS_ENDPOINT = "/products";
    private static final String PRODUCTS_ENDPOINT_WITH_ID = "/products/{productId}";

    private final WebClient webClient;
    private final ApplicationUserService applicationUserService;

    public List<Product> findAll() {
        var token = applicationUserService.getCurrentToken();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(PRODUCTS_ENDPOINT)
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
        var token = applicationUserService.getCurrentToken();
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
        var token = applicationUserService.getCurrentToken();
        return webClient.put()
                .uri(PRODUCTS_ENDPOINT_WITH_ID, product.getId())
                .body(Mono.just(product), Product.class)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public void delete(UUID id) {
        var token = applicationUserService.getCurrentToken();
        webClient.delete()
                .uri(PRODUCTS_ENDPOINT_WITH_ID, id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
