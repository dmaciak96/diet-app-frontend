package pl.daveproject.frontendservice.recipe.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.recipe.model.RecipeProductEntry;
import pl.daveproject.frontendservice.recipe.model.RecipeProductEntryRequest;
import pl.daveproject.frontendservice.recipe.model.RecipeRequest;
import pl.daveproject.frontendservice.user.UserService;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

  private static final String RECIPES_ENDPOINT = "/recipes";
  private static final String RECIPES_ENDPOINT_WITH_ID = "/recipes/{productId}";

  private final WebClient webClient;
  private final UserService userService;

  public List<Recipe> findAll() {
    var token = userService.getCurrentToken();
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(RECIPES_ENDPOINT)
            .build())
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToFlux(Recipe.class)
        .collectList()
        .block();
  }

  public Recipe saveOrUpdate(Recipe recipe) {
    if (recipe.getId() == null) {
      return save(recipe);
    } else {
      return update(recipe);
    }
  }

  private Recipe save(Recipe recipe) {
    if (recipe.getProducts() == null) {
      recipe.setProducts(List.of());
    }
    var token = userService.getCurrentToken();
    return webClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(RECIPES_ENDPOINT)
            .build())
        .body(Mono.just(toRequest(recipe)), RecipeRequest.class)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(Recipe.class)
        .block();
  }

  private Recipe update(Recipe recipe) {
    var token = userService.getCurrentToken();
    return webClient.put()
        .uri(RECIPES_ENDPOINT_WITH_ID, recipe.getId())
        .body(Mono.just(toRequest(recipe)), RecipeRequest.class)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(Recipe.class)
        .block();
  }

  public void delete(UUID id) {
    var token = userService.getCurrentToken();
    webClient.delete()
        .uri(RECIPES_ENDPOINT_WITH_ID, id)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(Recipe.class)
        .block();
  }

  private RecipeRequest toRequest(Recipe recipe) {
    return RecipeRequest.builder()
        .description(recipe.getDescription())
        .name(recipe.getName())
        .type(recipe.getType())
        .products(recipe.getProducts().stream()
            .map(this::toProductEntryRequest)
            .toList())
        .build();
  }

  private RecipeProductEntryRequest toProductEntryRequest(RecipeProductEntry
      recipeProductEntry) {
    return RecipeProductEntryRequest.builder()
        .productId(recipeProductEntry.getProduct().getId())
        .amountInGrams(recipeProductEntry.getAmountInGrams())
        .build();
  }


}
