package pl.daveproject.frontendservice.product.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    GRAINS("product-type.grains"),
    FRUITS_AND_VEGETABLES("product-type.fruits-and-vegetables"),
    DAIRY("product-type.dairy"),
    MEAT_AND_FISH("product-type.meat-and-fish"),
    FATS("product-type.fats");

    private final String translationKey;
}
