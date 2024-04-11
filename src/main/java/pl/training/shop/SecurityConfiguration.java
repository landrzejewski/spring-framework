package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import pl.training.shop.security.CustomEntryPoint;
import pl.training.shop.security.RequestUrlAuthorizationManager;
import pl.training.shop.security.SecurityContextLoggingFiler;
import pl.training.shop.security.jwt.JwtAuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

//@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
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
    public CorsConfiguration corsConfiguration() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4800"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextLoggingFiler securityContextLoggingFiler,
        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .addFilterBefore(securityContextLoggingFiler, ExceptionTranslationFilter.class)
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(config -> config.configurationSource(request -> corsConfiguration()))
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
                        //.usernameParameter("login")
                        //.passwordParameter("pass")
                )
                .logout(config -> config
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html"))
                        .logoutSuccessUrl("/login.html")
                        .invalidateHttpSession(true)
                )
                .build();
    }

}
