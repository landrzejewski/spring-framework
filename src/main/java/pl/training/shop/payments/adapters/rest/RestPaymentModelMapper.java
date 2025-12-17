package pl.training.shop.payments.adapters.rest;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentRequest;
import pl.training.shop.payments.domain.PaymentStatus;

@Component
public class RestPaymentModelMapper {

    public PaymentRequest toDomain(PaymentRequestDto paymentRequestDto) {
        var amount = Money.parse(paymentRequestDto.getValue());
        return new PaymentRequest(paymentRequestDto.getId(), amount);
    }

    public PaymentDto toDto(Payment payment) {
        var paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setStatus(payment.getStatus().name());
        paymentDto.setValue(payment.getValue().toString());
        paymentDto.setTimestamp(payment.getTimestamp());
        return paymentDto;
    }

    public PaymentStatus toDomain(String status) {
        return PaymentStatus.valueOf(status);
    }

}
