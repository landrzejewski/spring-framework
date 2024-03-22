package pl.training.shop.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.training.shop.security.jwt.*;

@Component
@RequiredArgsConstructor
@Log
public class SecurityInitializer implements ApplicationRunner {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("jan").isEmpty()) {
            var user = UserEntity.builder()
                    .username("jan")
                    .password(passwordEncoder.encode("123"))
                    .email("jan@training.pl")
                    .active(true)
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(user);
        }

        log.info("### " + jwtService.get("jan", "ROLE_ADMIN"));
     }

}
