package pl.training.shop.security;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Setter
@RequiredArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.username}")
    private String username;
    @Value("${security.password}")
    private String password;
    @Value("${security.email}")
    private String email;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (jpaUserRepository.findByUsername(username).isEmpty()) {
            var user = UserEntity.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .active(true)
                    .role("ROLE_ADMIN")
                    .build();
            jpaUserRepository.save(user);
        }
    }

}
