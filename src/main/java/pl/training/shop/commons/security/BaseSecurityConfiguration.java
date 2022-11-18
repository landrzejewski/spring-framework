package pl.training.shop.commons.security;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

public class BaseSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder) {
        var keycloakProvider = keycloakAuthenticationProvider();
        keycloakProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        authenticationManagerBuilder.authenticationProvider(keycloakProvider);
    }

    @Override
    protected AuthenticationEntryPoint authenticationEntryPoint() throws Exception {
        return new KeycloakAuthenticationEntryPoint(adapterDeploymentContext(), apiMatcher());
    }

    @Bean
    public CorsConfigurationSource corsConfiguration(@Value("${cors.origin}") String origin,
                                                     @Value("${cors.headers}") List<String> headers,
                                                     @Value("${cors.methods}") List<String> methods) {
        var configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(origin);
        configuration.setAllowedHeaders(headers);
        configuration.setAllowedMethods(methods);
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader(HttpHeaders.LOCATION);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AntPathRequestMatcher apiMatcher() {
        return new AntPathRequestMatcher("/api/**");
    }

}
