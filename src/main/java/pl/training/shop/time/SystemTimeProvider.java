package pl.training.shop.time;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component("timeProvider")
// Adnotacje poniżej to aliasy dla adnotacji @Component, pełnią rolę stereotypu (dodatkowa informacja dla innych programistów)
// @Controller
// @Service
// @Repository

// Scope wpływa na sposób zarządzania komponentem - decyduje o tym, kiedy jest on tworzony i niszczony przez kontener
// SINGLETON - instancja tworzona raz (przy starcie aplikacji) i reużywana (default)
// PROTOTYPE - instancja tworzona na życzenie (za każdym razem)

// @Scope("singleton")
// @Scope(SCOPE_SINGLETON)
public class SystemTimeProvider implements TimeProvider {

    @Override
    public Instant getTimestamp() {
        return Instant.now();
    }

}
