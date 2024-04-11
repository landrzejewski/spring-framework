package pl.training.shop.payments;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.shop.payments.domain.*;
import pl.training.shop.payments.ports.PaymentRepository;
import pl.training.shop.payments.ports.TimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PaymentProcessor paymentProcessor(
            PaymentIdGenerator uuidPaymentIdGenerator,
            PaymentFeeCalculator paymentFeeCalculator,
            @Qualifier("jpaPaymentRepositoryAdapter") PaymentRepository paymentRepository,
            TimeProvider timeProvider) {
        return new PaymentProcessor(uuidPaymentIdGenerator, paymentFeeCalculator, paymentRepository, timeProvider);
    }

    @Bean
    public PaymentFeeCalculator percentagePaymentFeeCalculator() {
        return new PercentagePaymentFeeCalculator(0.01);
    }

    @Bean
    public PaymentIdGenerator uuidPaymentIdGenerator() {
        return new UuidPaymentIdGenerator();
    }

}
