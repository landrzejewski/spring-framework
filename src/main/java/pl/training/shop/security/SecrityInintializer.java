package pl.training.shop.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.training.shop.security.jwt.JwtService;

import java.util.Set;

@Log
@Component
@RequiredArgsConstructor
public class SecrityInintializer implements ApplicationRunner {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("jan").isEmpty()) {
            var user = UserEntity.builder()
                    .username("jan")
                    .password(passwordEncoder.encode("123"))
                    .enabled(true)
                    .verified(true)
                    .roles("ROLE_ADMIN,ROLE_USER")
                    .build();
            userRepository.save(user);
        }
        var token = jwtService.createToken("jan", Set.of("ROLE_ADMIN"));
        log.info("JWT Token: " + token);
    }

}
