package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

// @EnableMongoRepositories
// @EnableJpaRepositories(repositoryImplementationPostfix = "Impl")
// @Profile("dev")
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    // @Profile("dev")
    @Scope(SCOPE_PROTOTYPE) // domyślnie scope jest ustawiony na SINGLETON
    @Bean({"timeProvider", "systemTimeProvider"}) // nadpisanie nazwy/nazw
    public TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("login.html").setViewName("login-form");
    }

}
