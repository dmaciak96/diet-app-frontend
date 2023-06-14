package pl.daveproject.frontendservice.registration.model;

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

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Double weight;
    private Double height;
    private Integer age;
    private Gender gender;
    private ActivityLevel activityLevel;
    private Byte[] photo;
}
