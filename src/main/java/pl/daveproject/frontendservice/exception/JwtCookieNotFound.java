package pl.daveproject.frontendservice.exception;

import pl.daveproject.frontendservice.BaseRestService;

public class JwtCookieNotFound extends RuntimeException {
    public JwtCookieNotFound() {
        super("Cookie %s not found, please authenticate first to generate JWT token"
                .formatted(BaseRestService.JWT_COOKIE_NAME));
    }
}
