package pl.training.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
     }

}
