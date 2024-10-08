package pl.training.shop.payments;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.training.shop.time.TimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PaymentProcessor paymentProcessor(
            PaymentIdGenerator uuidPaymentIdGenerator,
            // PaymentIdGenerator uuidPaymentIdGenerator,
            // @Qualifier("uuidPaymentIdGenerator") PaymentIdGenerator paymentIdGenerator,
            PaymentFeeCalculator paymentFeeCalculator,
            PaymentRepository paymentRepository,
            TimeProvider timeProvider) {
        return new PaymentProcessor(uuidPaymentIdGenerator, /*uuidPaymentIdGenerator()*/ paymentFeeCalculator, paymentRepository, timeProvider);
    }

    @Bean
    public PaymentFeeCalculator percentagePaymentFeeCalculator() {
        return new PercentagePaymentFeeCalculator(0.01);
    }

    @Primary
    @Bean
    public PaymentIdGenerator uuidPaymentIdGenerator() {
        return new UuidPaymentIdGenerator();
    }

    @Bean
    public PaymentIdGenerator fakePaymentIdGenerator() {
        return new FakePaymentIdGenerator();
    }

    @Bean
    public PaymentRepository inMemoryPaymentRepository() {
        return new InMemoryPaymentRepository();
    }

    @Bean
    public ConsolePaymentLoggerAspect consolePaymentLogger() {
        return new ConsolePaymentLoggerAspect();
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
