package pl.daveproject.frontendservice.registration.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {

    @Email
    private String email;

    @Size(min = 8, max = 255)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Min(1)
    private Double weight;

    @Min(1)
    private Double height;

    @Min(1)
    private Integer age;

    private Gender gender;
    private ActivityLevel activityLevel;
    private Byte[] photo;
}
