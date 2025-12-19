package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import pl.training.shop.security.GitHubGrantedAuthoritiesMapper;
import pl.training.shop.security.KeycloakAuthoritiesConverter;
import pl.training.shop.security.KeycloakGrantedAuthoritiesMapper;
import pl.training.shop.security.KeycloakLogoutHandler;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {


    @Bean
    public CorsConfiguration corsConfiguration() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("https://domena.pl"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfiguration corsConfiguration) throws Exception {
        return http
                .cors(config -> config.configurationSource(request -> corsConfiguration))
                .csrf(config -> config.ignoringRequestMatchers("/api/**"))
                .oauth2ResourceServer(config -> config.jwt(withDefaults()))
                .oauth2Login(config -> config.userInfoEndpoint(this::userInfoCustomizer))
                .authorizeHttpRequests(config -> config
                        .anyRequest().hasRole("ADMIN")
                )
                .logout(config -> config
                        .logoutSuccessUrl("/index.html")
                        .invalidateHttpSession(true)
                        .addLogoutHandler(new KeycloakLogoutHandler(new RestTemplate()))
                )
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    // Client scopes -> Client scope details (roles) -> Mapper details -> Add to userinfo enabled (Keycloak Admin console)
    private void userInfoCustomizer(OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig userInfoEndpointConfig) {
        userInfoEndpointConfig.userAuthoritiesMapper(new GitHubGrantedAuthoritiesMapper());
    }

}
