package pl.training.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecrityInintializer implements ApplicationRunner {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
    }

}
