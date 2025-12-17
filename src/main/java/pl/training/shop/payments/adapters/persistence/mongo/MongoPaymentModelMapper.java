package pl.training.shop.payments.adapters.persistence.mongo;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;

@Component
public class MongoPaymentModelMapper {

    public PaymentDocument toDocument(Payment payment) {
        var paymentDocument = new PaymentDocument();
        paymentDocument.setId(payment.getId());
        paymentDocument.setValue(payment.getValue().getNumber().doubleValue());
        paymentDocument.setCurrencyCode(payment.getValue().getCurrency().getCurrencyCode());
        paymentDocument.setStatus(payment.getStatus().name());
        paymentDocument.setTimestamp(payment.getTimestamp());
        return paymentDocument;
    }

    public Payment toDomain(PaymentDocument paymentDocument) {
        return Payment.builder()
                .id(paymentDocument.getId())
                .value(Money.of(paymentDocument.getValue(), paymentDocument.getCurrencyCode()))
                .status(PaymentStatus.valueOf(paymentDocument.getStatus()))
                .timestamp(paymentDocument.getTimestamp())
                .build();
    }

}
