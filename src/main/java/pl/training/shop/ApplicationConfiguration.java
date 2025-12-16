package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

// @Profile("dev")
@Configuration
public class ApplicationConfiguration {

    // @Profile("dev")
    @Scope(SCOPE_PROTOTYPE) // domyślnie scope jest ustawiony na SINGLETON
    @Bean({"timeProvider", "systemTimeProvider"}) // nadpisanie nazwy/nazw
    public TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

}
