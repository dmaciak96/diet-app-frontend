package pl.daveproject.frontendservice.bmi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UnitSystem {
    IMPERIAL("unit-system.imperial"),
    METRIC("unit-system.metric");

    private final String translationKey;
}
