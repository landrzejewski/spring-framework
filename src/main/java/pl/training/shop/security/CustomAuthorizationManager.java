package pl.training.shop.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authenticationSupplier, RequestAuthorizationContext context) {
        // var request = context.getRequest();
        var authentication = authenticationSupplier.get();
        return () -> authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
