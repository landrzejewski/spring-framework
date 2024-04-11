package pl.training.shop.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class JwtService {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("secret");
    private static final String ISSUER = "https://localhost:8000";
    private static final String USER_CLAIM = "user";
    private static final String ROLE_CLAIM = "role";
    private static final long EXPIRATION_PERIOD = 3600;

    public String getToken(String username, String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim(USER_CLAIM, username)
                .withClaim(ROLE_CLAIM, role)
                .withExpiresAt(Instant.now().plusSeconds(EXPIRATION_PERIOD))
                .sign(ALGORITHM);
    }

    public Optional<String> verifyToken(String token) {
        var verifier = JWT.require(ALGORITHM)
                .withIssuer(ISSUER)
                .build();
        try {
            var decoded = verifier.verify(token);
            return Optional.of(decoded.getClaim(ROLE_CLAIM).asString());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

    }

}
