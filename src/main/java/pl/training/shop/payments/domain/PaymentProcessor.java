package pl.training.shop.payments.domain;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import pl.training.shop.commons.aop.Atomic;
import pl.training.shop.commons.aop.Loggable;
import pl.training.shop.time.TimeProvider;

@Atomic
@Log
@RequiredArgsConstructor
public class PaymentProcessor {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    @Setter
    private TimeProvider timeProvider;

    // @Lock(type = WRITE)
    // @Retry
    // @Timer(timeUnit = MS)
    @Loggable
    public Payment process(PaymentRequest paymentRequest) {
        var paymentValue = calculatePaymentValue(paymentRequest.getValue());
        var payment = createPayment(paymentValue);
        return paymentsRepository.save(payment);
    }

    private Payment createPayment(Money paymentValue) {
        return Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentValue)
                .timestamp(timeProvider.getTimestamp())
                .status(PaymentStatus.STARTED)
                .build();
    }

    private Money calculatePaymentValue(Money paymentValue) {
        var paymentFee = paymentFeeCalculator.calculateFee(paymentValue);
        return paymentValue.add(paymentFee);
    }

}
