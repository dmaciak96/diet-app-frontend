package pl.daveproject.frontendservice.registration.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityLevel {
    NONE("activity-level.none"),
    SMALL("activity-level.low"),
    MEDIUM("activity-level.medium"),
    BIG("activity-level.high"),
    VERY_BIG("activity-level.very-high");

    private final String translationKey;
}
