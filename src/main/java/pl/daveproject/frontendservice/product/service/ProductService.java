package pl.daveproject.frontendservice.product.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.user.UserService;
import pl.daveproject.frontendservice.product.model.Product;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private static final String PRODUCTS_ENDPOINT = "/products";
    private static final String PRODUCTS_ENDPOINT_WITH_ID = "/products/{productId}";

    private final WebClient webClient;
    private final UserService userService;

    public List<Product> findAll() {
        var token = userService.getCurrentToken();
        var products = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(PRODUCTS_ENDPOINT)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
        return products;
    }

    public Product saveOrUpdate(Product product) {
        if (product.getId() == null) {
            return save(product);
        } else {
            return update(product);
        }
    }

    private Product save(Product product) {
        if(product.getParameters() == null) {
            product.setParameters(List.of());
        }
        var token = userService.getCurrentToken();
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
        var token = userService.getCurrentToken();
        return webClient.put()
                .uri(PRODUCTS_ENDPOINT_WITH_ID, product.getId())
                .body(Mono.just(product), Product.class)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public void delete(UUID id) {
        var token = userService.getCurrentToken();
        webClient.delete()
                .uri(PRODUCTS_ENDPOINT_WITH_ID, id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
