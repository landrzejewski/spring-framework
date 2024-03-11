package pl.training.shop;

import org.springframework.context.annotation.*;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@EnableAspectJAutoProxy
@ComponentScan
@Configuration
public class ApplicationConfiguration {

    @Scope(SCOPE_PROTOTYPE) // domyślnie scope jest ustawiony na SINGLETON
    @Bean({"timeProvider", "systemTimeProvider"}) // nadpisanie nazwy/nazw
    public TimeProvider timeProvider() { // domyślnie nazwa metody to nazwa beana
        return new SystemTimeProvider();
    }

}
