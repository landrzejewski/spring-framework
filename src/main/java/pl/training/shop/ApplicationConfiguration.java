package pl.training.shop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

}
