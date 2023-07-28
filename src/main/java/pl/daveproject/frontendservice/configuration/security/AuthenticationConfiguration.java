package pl.daveproject.frontendservice.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class AuthenticationConfiguration {

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter(
      KeycloakRoleConverter keycloakRoleConverter) {
    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);
    return converter;
  }
}
