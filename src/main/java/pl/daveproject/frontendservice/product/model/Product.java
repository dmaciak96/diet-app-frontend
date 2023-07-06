package pl.daveproject.frontendservice.product.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private UUID id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Min(0)
    private Double kcal;

    @NotNull
    private ProductType type;

    private List<ProductParameter> parameters = new ArrayList<>();
}
