package pl.daveproject.frontendservice.bmi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BmiRate {
    UNDERWEIGHT("bmi-rate.underweight"),
    CORRECT_VALUE("bmi-rate.correct-value"),
    OVERWEIGHT("bmi-rate.overweight"),
    FIRST_OBESITY_DEGREE("bmi-rate.first-obesity-degree"),
    SECOND_OBESITY_DEGREE("bmi-rate.second-obesity-degree"),
    THIRD_OBESITY_DEGREE("bmi-rate.third-obesity-degree");

    private final String translationKey;
}
