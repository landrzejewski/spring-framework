package pl.training.shop.payments.adapters.persistence.jpa;

import org.javamoney.moneta.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.training.shop.commons.data.PageDefinition;
import pl.training.shop.commons.data.ResultPage;
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

    public ResultPage<Payment> toDomain(Page<PaymentEntity> paymentEntityPage) {
        var payments = paymentEntityPage.getContent().stream()
                .map(this::toDomain)
                .toList();
        return new ResultPage<>(payments, paymentEntityPage.getTotalPages(), paymentEntityPage.getNumber());
    }

    public PageRequest toEntity(PageDefinition  pageDefinition) {
        return PageRequest.of(pageDefinition.getNumber(), pageDefinition.getSize());
    }

}
