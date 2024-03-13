package pl.training.shop.payments.adapters.persistence.mongo;

import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.shop.payments.domain.Payment;

@Mapper(componentModel = "spring", imports = {Money.class})
public interface MongoPaymentRepositoryMapper {

    @Mapping(target = "value", expression = "java(payment.getValue().getNumber().doubleValueExact())")
    @Mapping(target = "currencyCode", expression = "java(payment.getValue().getCurrency().getCurrencyCode())")
    PaymentDocument toDocument(Payment payment);

    @Mapping(target = "value", expression = "java(Money.of(paymentDocument.getValue(), paymentDocument.getCurrencyCode()))")
    Payment toDomain(PaymentDocument paymentDocument);

}
