package pl.training.shop;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.training.shop.commons.security.RestTemplateTokenInterceptor;
import pl.training.shop.payments.adapters.time.SystemTimeProvider;
import pl.training.shop.payments.ports.TimeProvider;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Bean
    public TimeProvider systemTimeProvider() {
        return new SystemTimeProvider();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("login.html").setViewName("login-form");
        registry.addViewController("payments/payment-summary").setViewName("payments/payment-summary");
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .additionalInterceptors(new RestTemplateTokenInterceptor())
                .build();
    }

}
