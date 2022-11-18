package pl.training.shop;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import pl.training.shop.commons.security.BaseSecurityConfiguration;

@KeycloakConfiguration
public class SecurityConfiguration extends BaseSecurityConfiguration {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf()
                .ignoringRequestMatchers(apiMatcher())
                .and()
                .authorizeRequests()
                .mvcMatchers("/payments/process").hasRole("ADMIN")
                .mvcMatchers("/api/**").hasRole("ADMIN")
                .mvcMatchers("/**").permitAll();
    }

}
