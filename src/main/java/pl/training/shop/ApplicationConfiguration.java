package pl.training.shop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableMongoRepositories
//@EnableJpaRepositories(repositoryImplementationPostfix = "Impl")
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/api/*")
               .allowedOrigins("http://localhost:4200")
               .allowedHeaders("*")
               .allowedMethods("GET", "POST", "PUT");
    }

}
