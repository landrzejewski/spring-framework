package pl.training.shop.commons.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class KeycloakToken {

    private static final String TOKEN_PREFIX = "Bearer ";

    @SuppressWarnings("unchecked")
    public static Optional<String> get() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal())
                .map(principal -> principal.getKeycloakSecurityContext().getTokenString());
    }

    public static Optional<String> getAuthorizationHeader() {
        return get().map(token -> TOKEN_PREFIX + token);
    }

}
