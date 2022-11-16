package pl.training.shop.payments.domain;

import lombok.Value;
import org.javamoney.moneta.Money;

@Value
public class PaymentRequestDomain {

    Long id;
    Money value;

}
