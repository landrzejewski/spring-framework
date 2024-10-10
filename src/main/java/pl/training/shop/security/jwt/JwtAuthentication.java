package pl.training.shop.security.jwt;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import static java.util.Collections.emptyList;

public class JwtAuthentication extends AbstractAuthenticationToken {

    @Getter
    private String token;

    public JwtAuthentication(String token) {
        super(emptyList());
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
