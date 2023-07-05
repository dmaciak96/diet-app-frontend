package pl.daveproject.frontendservice;

import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import pl.daveproject.frontendservice.exception.UserNotLoginException;
import pl.daveproject.frontendservice.ui.login.model.LoginResponse;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class BaseRestService {
    public static final String JWT_COOKIE_NAME = "jwt-token";

    @Value("${jwt.cookie.expiration.seconds}")
    private int JWT_COOKIE_EXPIRATION_SECONDS;

    protected String getJwtToken() {
        return getCookieByName(BaseRestService.JWT_COOKIE_NAME)
                .orElseThrow(UserNotLoginException::new).getValue();
    }

    protected Optional<Cookie> getCookieByName(String cookieName) {
        return Arrays.stream(VaadinService.getCurrentRequest().getCookies())
                .filter(cookie -> StringUtils.isNotBlank(cookie.getName()) && cookie.getName().equals(cookieName))
                .findFirst();
    }

    public void saveJwtToken(LoginResponse loginResponse) {
        if (loginResponse == null) {
            log.warn("Cannot save JWT token, login response is null");
            return;
        }
        log.debug("Saving JWT token in cookie {}...", JWT_COOKIE_NAME);
        var cookie = new Cookie(JWT_COOKIE_NAME, loginResponse.token());
        cookie.setMaxAge(JWT_COOKIE_EXPIRATION_SECONDS);
        cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        VaadinService.getCurrentResponse().addCookie(cookie);
        log.debug("JWT token saved successfully");
    }
}
