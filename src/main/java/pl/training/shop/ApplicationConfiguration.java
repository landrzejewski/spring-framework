package pl.training.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.training.shop.security.jwt.JwtAuthenticationProvider;
import pl.training.shop.security.jwt.JwtService;

//@EnableMongoRepositories
//@EnableJpaRepositories(repositoryImplementationPostfix = "Impl")
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/api/*")
               .allowedOrigins("http://localhost:4200")
               .allowedHeaders("*")
               .allowedMethods("GET", "POST", "PUT");
    }*/

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index");
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("/login.html").setViewName("login-form");
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        var daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        builder.authenticationProvider(daoAuthenticationProvider);

        builder.authenticationProvider(new JwtAuthenticationProvider(jwtService));
    }

}
