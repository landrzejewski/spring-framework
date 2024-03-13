package pl.training.shop.payments.adapters.persistence.mongo;

import org.javamoney.moneta.Money;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentStatus;

import java.util.List;

@Mapper(componentModel = "spring", imports = {Money.class})
public interface MongoPaymentRepositoryMapper {

    @Mapping(target = "value", expression = "java(payment.getValue().getNumber().doubleValueExact())")
    @Mapping(target = "currencyCode", expression = "java(payment.getValue().getCurrency().getCurrencyCode())")
    PaymentDocument toDocument(Payment payment);

    @Mapping(target = "value", expression = "java(Money.of(paymentDocument.getValue(), paymentDocument.getCurrencyCode()))")
    Payment toDomain(PaymentDocument paymentDocument);

    String toDocument(PaymentStatus paymentStatus);

    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toDomain(List<PaymentDocument> paymentDocuments);

    @Mapping(source = "content", target = "data")
    @Mapping(source = "number", target = "pageNumber")
    ResultPage<Payment> toDomain(Page<PaymentDocument> paymentDocumentPage);


}
