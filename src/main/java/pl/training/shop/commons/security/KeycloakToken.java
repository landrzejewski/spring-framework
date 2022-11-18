package pl.training.shop.commons.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.Token;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class KeycloakToken {

    private static final String TOKEN_PREFIX = "Bearer ";

    public static Optional<String> getTokenString() {
        return getPrincipal().map(KeycloakSecurityContext::getTokenString);
    }

    public static Optional<Token> getToken() {
        return getPrincipal().map(KeycloakSecurityContext::getToken);
    }

    @SuppressWarnings("unchecked")
    public static Optional<KeycloakSecurityContext> getPrincipal() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal())
                .map(KeycloakPrincipal::getKeycloakSecurityContext);
    }

    public static Optional<String> getAuthorizationHeader() {
        return getTokenString().map(token -> TOKEN_PREFIX + token);
    }

}
