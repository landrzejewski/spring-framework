package pl.training.shop.payments.adapters.persistence.jpa;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;

@Component
public class JpaPaymentModelMapper {

    public PaymentEntity toEntity(Payment payment) {
        var paymentEntity = new PaymentEntity();
        paymentEntity.setId(payment.getId());
        paymentEntity.setValue(payment.getValue().getNumber().doubleValue());
        paymentEntity.setCurrencyCode(payment.getValue().getCurrency().getCurrencyCode());
        paymentEntity.setStatus(payment.getStatus().name());
        paymentEntity.setTimestamp(payment.getTimestamp());
        return paymentEntity;
    }

    public Payment toDomain(PaymentEntity paymentEntity) {
        return Payment.builder()
                .id(paymentEntity.getId())
                .value(Money.of(paymentEntity.getValue(), paymentEntity.getCurrencyCode()))
                .status(PaymentStatus.valueOf(paymentEntity.getStatus()))
                .timestamp(paymentEntity.getTimestamp())
                .build();
    }

}
