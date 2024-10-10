package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import pl.training.shop.security.BasicAuthenticationEntryPoint;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*public UserDetails userDetails() {
        return User
                .withUsername("jan")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN", "USER")
                .authorities("read", "write")
                .build();

    }*/

    /*@Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userDetails();
            if (username.equals(user.getUsername())) {
                return user;
            } else {
                throw new UsernameNotFoundException(username);
            }
        };
    }*/

   /* @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        // return new InMemoryUserDetailsManager(userDetails());
        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from users where username = ?");
        manager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?");
        return manager;
    }*/

    @Bean
    public CorsConfiguration corsConfiguration() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4800"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(config -> config.configurationSource(request -> corsConfiguration()))
                .csrf(config -> config
                        .ignoringRequestMatchers("/api/**")
                )
                .httpBasic(config -> config
                        .realmName("Training")
                        .authenticationEntryPoint(new BasicAuthenticationEntryPoint())
                )
                .formLogin(config -> config
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/index.html")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .authorizeHttpRequests(config -> config
                                .requestMatchers("/api/ttt").permitAll()
                                .requestMatchers("/login.html").permitAll()
                                .requestMatchers(POST, "payments/process").hasRole("ADMIN")
                                .anyRequest().authenticated()
                        //.anyRequest().access(new RequestUrlAuthorizationManager())
                )
                .logout(config -> config
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html"))
                        //.logoutUrl("/logout.html")
                        .invalidateHttpSession(true)
                )
                .build();
    }

}
