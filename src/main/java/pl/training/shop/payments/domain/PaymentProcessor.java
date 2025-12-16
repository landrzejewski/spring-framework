package pl.training.shop.payments.domain;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.commons.aop.*;
import pl.training.shop.time.TimeProvider;

@Atomic
@Log
@RequiredArgsConstructor
public class PaymentProcessor implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    @Setter
    private TimeProvider timeProvider;

    // @Lock(type = WRITE)
    // @Retry
    // @Timer(timeUnit = MS)
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
        return paymentsRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    // Wymagania dla metod związanych z cyklem życia
    // - brak argumentów
    // - brak resultatu
    // - brak wyjątków typu Exception

    // Metoda do inicjalizaji - wołana po wstrzyknięciu wszystkich zależności
    public void init() {
        log.info("Initializing Payment processor");
    }

    // Metoda do sprzątania - wołana po przed zniszczeniem beana (zwolnieniem referencji), działa tylko dal scope SINGLETON i przy prawidłowym zatrzymaniu kontenera
    public void destroy() {
        log.info("Destroying Payment processor");
    }

}
