package pl.daveproject.frontendservice.caloricneeds.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("gender.male"),
    FEMALE("gender.female");

    private final String translationKey;
}
