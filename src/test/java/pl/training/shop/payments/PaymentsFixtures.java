package pl.training.shop.payments;

import org.javamoney.moneta.Money;
import org.mockito.stubbing.Answer;
import pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity;
import pl.training.shop.payments.adapters.rest.PaymentDto;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;

import java.time.Instant;

import static pl.training.shop.payments.domain.PaymentStatus.STARTED;

public class PaymentsFixtures {

    public static final String PAYMENT_ID = "4828124d-e43e-4f59-a5f6-3cbfecc9898f";
    public static final String CURRENCY_CODE = "PLN";
    public static final Money PAYMENT_VALUE = Money.of(1_000, CURRENCY_CODE);
    public static final Instant TIMESTAMP = Instant.now();
    public static final Payment PAYMENT = Payment.builder()
            .id(PAYMENT_ID)
            .value(PAYMENT_VALUE)
            .timestamp(TIMESTAMP)
            .status(STARTED)
            .build();

    public static PaymentEntity createEntity(String status) {
        var entity = new PaymentEntity();
        entity.setId(PAYMENT_ID);
        entity.setValue(PAYMENT_VALUE.getNumber().doubleValueExact());
        entity.setCurrencyCode(CURRENCY_CODE);
        entity.setTimestamp(TIMESTAMP);
        entity.setStatus(status);
        return entity;
    }

    public static final Answer<PaymentDto> toDto = (invocation) -> {
        var payment = invocation.getArgument(0, Payment.class);
        var paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setValue(payment.getValue().toString());
        return paymentDto;
    };

}
