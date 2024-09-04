package pl.training.shop.payments.adapters.rest;

import org.mapstruct.Mapper;
import pl.training.shop.commons.data.MoneyMapper;
import pl.training.shop.commons.data.Page;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentRequest;
import pl.training.shop.payments.domain.PaymentStatus;

@Mapper(componentModel = "spring", uses = MoneyMapper.class)
public interface PaymentRestMapper {

    PaymentRequest toDomain(PaymentRequestDto paymentRequestDto);

    PaymentDto toDto(Payment payment);

    ResultPage<PaymentDto> toDto(ResultPage<Payment> payments);

    default PaymentStatus toDomain(String status) {
        return PaymentStatus.valueOf(status);
    }

    default Page toDomain(int pageNumber, int pageSize) {
        return new Page(pageNumber, pageSize);
    }

}
