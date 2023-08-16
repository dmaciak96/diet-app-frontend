package pl.daveproject.frontendservice.caloricneeds.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.daveproject.frontendservice.bmi.model.UnitSystem;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TotalCaloricNeeds {
    private UUID id;

    @NotNull(message = "Weight cannot be null")
    @Min(value = 0, message = "Weight cannot be less than 0")
    private double weight;

    @NotNull(message = "Height cannot be null")
    @Min(value = 0, message = "Height cannot be less than 0")
    private double height;

    @NotNull(message = "Unit cannot be null")
    private UnitSystem unit;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Activity level cannot be null")
    private ActivityLevel activityLevel;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age cannot be less than 0")
    private int age;

    private double value;

    private LocalDate addedDate;

    public double getValue() {
        return (double) Math.round(value * 100) / 100;
    }

    public double getWeight() {
        return (double) Math.round(weight * 100) / 100;
    }

    public double getHeight() {
        return (double) Math.round(height * 100) / 100;
    }
}
