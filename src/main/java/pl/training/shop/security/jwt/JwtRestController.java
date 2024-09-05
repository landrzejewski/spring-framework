package pl.training.shop.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class JwtRestController {

    private final JwtService jwtService;

    @PostMapping("api/tokens")
    public ResponseEntity<TokenDto> login(@RequestBody CredentialsDto credentials) {
        var loginResult = login(credentials.getUsername(), credentials.getPassword());
        if (loginResult.isPresent()) {
            var token = jwtService.createToken(credentials.getUsername(), loginResult.get());
            var tokenDto = new TokenDto(token);
            return ResponseEntity.ok(tokenDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    private Optional<Set<String>> login(String login, String password) {
        if (login.equals("jan") && password.equals("123")) {
            return Optional.of(Set.of("ROLE_ADMIN"));
        } else {
            return Optional.empty();
        }
    }

}
