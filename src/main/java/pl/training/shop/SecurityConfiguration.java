package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.training.shop.security.CustomEntryPoint;

import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDetails user() {
        return User.withUsername("jan")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN", "TESTER") // ROLE_ADMIN
                .authorities("read", "write")
                .build();
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (!username.equals("jan")) {
                throw new UsernameNotFoundException(username);
            }
            return user();
        };
    }*/

    /*@Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        // return new InMemoryUserDetailsManager(user());

        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from app_users where username = ?");
        manager.setAuthoritiesByUsernameQuery("select username, authority from app_users_authorities where username = ?");
        return manager;
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(config -> config
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(config -> config
                        .requestMatchers("/login.html").permitAll()
                        .requestMatchers(POST, "/payments/process").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(config -> config
                        .realmName("Training")
                        .authenticationEntryPoint(new CustomEntryPoint())
                )
                .formLogin(config -> config
                        .loginPage("/login.html")
                )
                .build();
    }

}
