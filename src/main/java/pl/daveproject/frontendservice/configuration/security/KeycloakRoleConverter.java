package pl.daveproject.frontendservice.configuration.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {
    if (!source.getClaims().containsKey("realm_access")) {
      return Collections.emptyList();
    }
    var realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
    return ((List<String>) realmAccess.get("roles")).stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
