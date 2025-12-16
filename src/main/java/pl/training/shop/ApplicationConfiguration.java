package pl.training.shop;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.*;
import pl.training.shop.commons.aop.CacheAspect;
import pl.training.shop.payments.PaymentCreatedEventListener;
import pl.training.shop.payments.PaymentCreatedPublisher;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

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

}
