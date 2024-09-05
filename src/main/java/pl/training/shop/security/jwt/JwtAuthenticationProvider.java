package pl.training.shop.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated;

// @Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication jwtAuthentication) {
            var token = jwtAuthentication.getToken();
            return jwtService.verify(token)
                    .map(principal -> {
                        var roles = principal.roles().stream().map(SimpleGrantedAuthority::new).toList();
                        return authenticated(principal.username(), token, roles);
                    })
                    .orElseThrow(() -> new BadCredentialsException("Invalid token"));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

}
