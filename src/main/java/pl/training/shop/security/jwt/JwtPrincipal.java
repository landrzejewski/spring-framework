package pl.training.shop.security.jwt;

import java.util.Set;

public record JwtPrincipal(String username, Set<String> roles) {
}
