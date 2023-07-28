package pl.daveproject.frontendservice.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import pl.daveproject.frontendservice.exception.UserNotLoginException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    public DefaultOidcUser getCurrentUser() {
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            return (DefaultOidcUser) authentication.getPrincipal();
        }
        throw new UserNotLoginException();
    }

    public String getCurrentToken() {
        return getCurrentUser().getIdToken().getTokenValue();
    }
}
