package pl.training.shop.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.training.shop.time.TimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PaymentProcessor paymentProcessor(PaymentIdGenerator paymentIdGenerator,
                                             // @Qualifier("uuid") PaymentIdGenerator paymentIdGenerator,
                                             PaymentFeeCalculator paymentFeeCalculator,
                                             PaymentRepository paymentRepository,
                                             TimeProvider timeProvider) {
        var processor = new PaymentProcessor(paymentIdGenerator, paymentFeeCalculator, paymentRepository /*inMemoryPaymentRepository()*/);
        processor.setTimeProvider(timeProvider);
        return processor;
    }

    @Bean
    public PaymentFeeCalculator paymentFeeCalculator() {
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
    public PaymentRepository paymentRepository() {
        return new JpaPaymentRepository();
    }

    @Bean
    public ConsolePaymentLoggerAspect consolePaymentLoggerAspect() {
        return new ConsolePaymentLoggerAspect();
    }

}
