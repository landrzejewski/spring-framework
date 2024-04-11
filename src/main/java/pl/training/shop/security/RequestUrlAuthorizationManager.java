package pl.training.shop.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class RequestUrlAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final Map<String, Set<RequestMatcher>> mappings = Map.of(
            "ROLE_ADMIN", Set.of(
                    new AntPathRequestMatcher("/api/users/**"),
                    new AntPathRequestMatcher("/**")
            ),
            "ROLE_USER", Set.of(
                    new AntPathRequestMatcher("/api/payments/**")
            )
    );

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        var request = context.getRequest();
        boolean hasAccess = getRoles(authentication)
                .stream()
                .anyMatch(role -> checkRole(role, request));
        return new AuthorizationDecision(hasAccess);
    }

    private Set<String> getRoles(Supplier<Authentication> authentication) {
        return authentication.get()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toSet());
    }

    private boolean checkRole(String role, HttpServletRequest request) {
        return mappings.getOrDefault(role, emptySet())
                .stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

}
