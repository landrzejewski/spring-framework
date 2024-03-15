package pl.training.shop.payments.domain;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.training.shop.payments.ports.PaymentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pl.training.shop.payments.PaymentsFixtures.*;
import static pl.training.shop.payments.domain.PaymentStatus.STARTED;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessorTest {

    private static final Money PAYMENT_FEE = Money.of(10, CURRENCY_CODE);
    private static final PaymentRequest PAYMENT_REQUEST = new PaymentRequest(1L, PAYMENT_VALUE);

    @Mock
    private PaymentRepository paymentRepository;
    private PaymentProcessor paymentProcessor;

    @BeforeEach
    void beforeEach() {
        when(paymentRepository.save(any(Payment.class))).then(AdditionalAnswers.returnsFirstArg());
        paymentProcessor = new PaymentProcessor(() -> PAYMENT_ID, (value) -> PAYMENT_FEE, paymentRepository, () -> TIMESTAMP);
    }

    @Test
    void given_valid_payment_request_when_process_then_returns_new_payment() {
        var expectedPayment = Payment.builder()
                .id(PAYMENT_ID)
                .value(PAYMENT_VALUE.add(PAYMENT_FEE))
                .timestamp(TIMESTAMP)
                .status(STARTED)
                .build();
        assertEquals(expectedPayment, paymentProcessor.process(PAYMENT_REQUEST));
    }

    /*@Test
    void given_valid_payment_request_when_process_then_payment_is_persisted() {
        var payment = paymentProcessor.process(PAYMENT_REQUEST);
        verify(paymentRepository).save(payment);
    }*/

}
