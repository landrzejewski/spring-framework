package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.commons.aspect.Loggable;
import pl.training.shop.commons.aspect.MinLength;
import pl.training.shop.commons.aspect.Retry;
import pl.training.shop.commons.aspect.Timer;
import pl.training.shop.time.TimeProvider;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static pl.training.shop.commons.aspect.Timer.TimeUnit.MS;
import static pl.training.shop.commons.aspect.Timer.TimeUnit.NS;

@Transactional(propagation = REQUIRES_NEW)
@Log
@RequiredArgsConstructor
public class PaymentProcessor implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    private final TimeProvider timeProvider;

    // @Retry(attempts = 2)
    // @Timer(timeUnit = NS)
    @Loggable
    @Override
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

    @Override
    public Payment getById(@MinLength(16) String id) {
        return paymentsRepository.getById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    public void init() {
        log.info("Initializing Payment processor");
    }

    public void destroy() {
        log.info("Destroying Payment processor");
    }

}
