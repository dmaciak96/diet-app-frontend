package pl.daveproject.frontendservice.shoppinglist.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.daveproject.frontendservice.recipe.model.Recipe;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingList;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingListDialogModel;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingListRequest;
import pl.daveproject.frontendservice.user.UserService;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingListService {

  private static final String SHOPPING_LISTS_ENDPOINT = "/shopping-lists";
  private static final String SHOPPING_LISTS_ENDPOINT_WITH_ID = "/shopping-lists/{productId}";

  private final WebClient webClient;
  private final UserService userService;

  public List<ShoppingList> findAll() {
    var token = userService.getCurrentToken();
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(SHOPPING_LISTS_ENDPOINT)
            .build())
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToFlux(ShoppingList.class)
        .collectList()
        .block();
  }

  public ShoppingList save(ShoppingListRequest shoppingListRequest) {
    if (shoppingListRequest.getRecipes() == null) {
      shoppingListRequest.setRecipes(List.of());
    }
    var token = userService.getCurrentToken();
    return webClient.post()
        .uri(uriBuilder -> uriBuilder
            .path(SHOPPING_LISTS_ENDPOINT)
            .build())
        .body(Mono.just(shoppingListRequest), ShoppingListRequest.class)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(ShoppingList.class)
        .block();
  }

  public void delete(UUID id) {
    var token = userService.getCurrentToken();
    webClient.delete()
        .uri(SHOPPING_LISTS_ENDPOINT_WITH_ID, id)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .retrieve()
        .bodyToMono(ShoppingList.class)
        .block();
  }

  public ShoppingListRequest toRequest(ShoppingListDialogModel dialogModel) {
    return ShoppingListRequest.builder()
        .name(dialogModel.getName())
        .recipes(dialogModel.getRecipes().stream().map(Recipe::getId).toList())
        .build();
  }

  public ShoppingListDialogModel toDialogModel(ShoppingList shoppingList) {
    return ShoppingListDialogModel.builder()
        .id(shoppingList.getId())
        .recipes(shoppingList.getRecipes())
        .name(shoppingList.getName())
        .build();
  }
}
