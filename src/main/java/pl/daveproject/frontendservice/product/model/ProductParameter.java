package pl.daveproject.frontendservice.product.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductParameter {

    private UUID id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    @Size(min = 1, max = 255)
    private String value;
}
