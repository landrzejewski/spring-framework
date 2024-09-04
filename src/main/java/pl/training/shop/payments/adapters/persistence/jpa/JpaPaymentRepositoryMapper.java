package pl.training.shop.payments.adapters.persistence.jpa;

import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.payments.domain.Payment;

@Mapper(componentModel = "spring", imports = {Money.class})
public interface JpaPaymentRepositoryMapper {

    @Mapping(target = "value", expression = "java(payment.getValue().getNumber().doubleValueExact())")
    @Mapping(target = "currencyCode", expression = "java(payment.getValue().getCurrency().getCurrencyCode())")
    PaymentEntity toEntity(Payment payment);

    @Mapping(target = "value", expression = "java(Money.of(paymentEntity.getValue(), paymentEntity.getCurrencyCode()))")
    Payment toDomain(PaymentEntity paymentEntity);

    @Mapping(source = "content", target = "data")
    @Mapping(source = "number", target = "pageNumber")
    ResultPage<Payment> toDomain(Page<PaymentEntity> resultPage);

}
