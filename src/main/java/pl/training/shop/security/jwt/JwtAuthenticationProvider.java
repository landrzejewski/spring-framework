package pl.training.shop.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;

//@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication jwtAuthentication) {
            var token = jwtAuthentication.getToken();
            return jwtService.verifyToken(token)
                    .map(role -> {
                        var roles = Set.of(new SimpleGrantedAuthority(role));
                        return UsernamePasswordAuthenticationToken.authenticated("", token, roles);
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
