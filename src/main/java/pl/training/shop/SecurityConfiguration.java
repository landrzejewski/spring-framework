package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import pl.training.shop.security.CustomEntryPoint;
import pl.training.shop.security.RequestUrlAuthorizationManager;
import pl.training.shop.security.SecurityContextLoggingFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

//@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true/*, prePostEnabled = true*/)
@Configuration
public class SecurityConfiguration {

    /*AuthenticationManager authenticationManager; // Interfejs/kontrakt dla procesu uwierzytelnienia użytkownika
        ProviderManager providerManager; // Podstawowa implementacja AuthenticationManager, deleguje proces uwierzytelnienia do jednego z obiektów AuthenticationProvider
            AuthenticationProvider authenticationProvider; // Interfejs/kontrakt dla obiektów realizujących uwierzytelnianie z wykorzystaniem konkretnego mechanizmu/implementacji
                DaoAuthenticationProvider daoAuthenticationProvider; // Jedna z implementacji AuthenticationProvider, ładuje dane o użytkowniku wykorzystując UserDetailsService i porównuje je z tymi podanymi w czasie logowani
                    UserDetailsService userDetailsService; // Interfejs/kontrakt usługi ładującej dane dotyczące użytkownika

    UsersDetailsManager usersDetailsManager; Interfejs/kontrakt pochodny UserDetailsService, pozwalający na zarządzanie użytkownikami
        InMemoryUserDetailsManager inMemoryUserDetailsManager; // Jedna z implementacji UsersDetailsManager, przechowuje informacje w pamięci

    PasswordEncoder passwordEncoder; //Interfejs/kontrakt pozwalający na hashowanie i porównywanie haseł
        BCryptPasswordEncoder bCryptPasswordEncoder; //Jedna z implementacji PasswordEncoder

    SecurityContextHolder securityContextHolder; // Przechowuje/udostępnia SecurityContext
        SecurityContext securityContext; // Kontener przechowujący Authentication
            Authentication authentication; // Reprezentuje dane uwierzytelniające jak i uwierzytelnionego użytkownika/system
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken; // Jedna z implementacji Authentication, zawiera login i hasło jako credentials
                    UserDetails userDetails; // Interfejs/kontrakt opisujący użytkownika
                    GrantedAuthority grantedAuthority; // Interfejs/kontrakt opisujący role/uprawnienia
                        SimpleGrantedAuthority simpleGrantedAuthority; // Jedna z implementacji SimpleGrantedAuthority

    AuthorizationManager authorizationManager; // Interfejs/kontrakt dla procesu autoryzacji
        AuthoritiesAuthorizationManager authoritiesAuthorizationManager; // Jedna z implementacji AuthorizationManager (role)*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetails user() {
        return User.withUsername("jan")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .authorities("read", "write")
                .build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (!username.equals("jan")) {
                throw new UsernameNotFoundException("User jan not found");
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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityContextLoggingFilter securityContextLoggingFilter) throws Exception {
        return httpSecurity
                .addFilterBefore(securityContextLoggingFilter, ExceptionTranslationFilter.class)

                .cors(config -> config.configurationSource(request -> corsConfiguration()))

                .csrf(config -> config.ignoringRequestMatchers("/api/**"))

                .authorizeHttpRequests(config -> config
                        .requestMatchers("/login.html").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )

                //.httpBasic(withDefaults())
                .httpBasic(config -> config
                        .realmName("Training")
                        .authenticationEntryPoint(new CustomEntryPoint())
                )
                //.formLogin(withDefaults())

                .formLogin(config -> config
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/index.html")
                        //.usernameParameter("username")
                        //.passwordParameter("password")
                        //.successHandler(new CustomAuthenticationSuccessHandler())
                        //.failureHandler(new CustomAuthenticationFailureHandler())
                )

                .logout(config -> config
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html"))
                        .logoutSuccessUrl("/login.html")
                        .invalidateHttpSession(true)
                )

                .build();
    }

}
