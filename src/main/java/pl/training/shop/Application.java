package pl.training.shop;

import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import pl.training.shop.payments.*;
import pl.training.shop.time.SystemTimeProvider;
import pl.training.shop.time.TimeProvider;

@Log
public class Application {

    private static final String DEFAULT_CURRENCY_CODE = "PLN";

    private static TimeProvider timeProvider() {
        return new SystemTimeProvider();
    }

    private static PaymentIdGenerator paymentIdGenerator() {
        return new UuidPaymentIdGenerator();
    }

    private static PaymentFeeCalculator paymentFeeCalculator() {
        return new PercentagePaymentFeeCalculator(0.01);
    }

    private static PaymentRepository paymentRepository() {
        return new InMemoryPaymentRepository();
    }

    private static PaymentService paymentService(PaymentIdGenerator paymentIdGenerator, PaymentFeeCalculator paymentFeeCalculator,
                                                 PaymentRepository  paymentRepository, TimeProvider timeProvider) {
        return new PaymentProcessor(paymentIdGenerator, paymentFeeCalculator, paymentRepository, timeProvider);
    }

    private static PaymentService paymentServiceWithLogging(PaymentService paymentService) {
        return new ConsoleLoggerPaymentServiceProxy(paymentService);
    }

    public static void main(String[] args) {
        var paymentsProcessor = paymentService(paymentIdGenerator(), paymentFeeCalculator(), paymentRepository(), timeProvider());
        var paymentService = paymentServiceWithLogging(paymentsProcessor);
        //-------------
        var paymentRequest = new PaymentRequest(1L, Money.of(1_000, DEFAULT_CURRENCY_CODE));
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }

}
