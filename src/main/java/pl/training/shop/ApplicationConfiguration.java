package pl.training.shop;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.training.shop.commons.aop.CacheAspect;
import pl.training.shop.payments.PaymentCreatedEventListener;
import pl.training.shop.payments.PaymentCreatedPublisher;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

import javax.sql.DataSource;

import java.util.Map;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

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
    public PaymentCreatedPublisher paymentCreatedPublisher(ApplicationEventPublisher publisher) {
        return new PaymentCreatedPublisher(publisher);
    }

    @Bean
    public PaymentCreatedEventListener paymentCreatedEventListener() {
        return new PaymentCreatedEventListener();
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.shop.payments.Payment pl.training.shop.payments.PaymentProcessor.getById(String))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

    @Bean
    public DataSource dataSource() {
       var dataSource = new HikariDataSource();
       dataSource.setUsername("admin");
       dataSource.setPassword("admin");
       dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/payments");
       dataSource.setDriverClassName("org.postgresql.Driver");
       return dataSource;
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
