package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import pl.training.shop.security.KeycloakGrantedAuthoritiesMapper;
import pl.training.shop.security.KeycloakJwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(config -> config.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(config -> config
                        .anyRequest().hasRole("ADMIN")
                )
                .oauth2ResourceServer(config -> config.jwt(this::jwtConfigurer))
                .oauth2Login(config -> config.userInfoEndpoint(this::userInfoCustomizer))
                .build();
    }

    private void jwtConfigurer(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtGrantedAuthoritiesConverter());
        jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter);
    }

    private void userInfoCustomizer(OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig userInfoEndpointConfig) {
        userInfoEndpointConfig.userAuthoritiesMapper(new KeycloakGrantedAuthoritiesMapper());
    }

}
