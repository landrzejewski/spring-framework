package pl.training.shop;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

import javax.sql.DataSource;
import java.util.Map;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan
@Configuration
public class ApplicationConfiguration {

    @Scope(SCOPE_PROTOTYPE) // domyślnie scope jest ustawiony na SINGLETON
    @Bean({"timeProvider", "systemTimeProvider"}) // nadpisanie nazwy/nazw
    public TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

    @Bean
    public DataSource dataSource(Environment environment) {
        var datasource = new HikariDataSource();
        datasource.setUsername(environment.getProperty("database.username"));
        datasource.setPassword(environment.getProperty("database.password"));
        datasource.setJdbcUrl(environment.getProperty("database.url"));
        datasource.setDriverClassName(environment.getProperty("database.driver"));
        datasource.setMaximumPoolSize(Integer.parseInt(environment.getProperty("database.pool-size")));
        return datasource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("pl.training.shop");
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setJpaPropertyMap(Map.of(
                "jakarta.persistence.schema-generation.database.action", "drop-and-create"
        ));
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
