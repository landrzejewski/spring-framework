package pl.training.shop.payments.adapters.persistence.jpa;

import org.springframework.beans.factory.annotation.Value;

public interface PaymentProjection {

    String getId();
    @Value("#{target.value + ' ' + target.currencyCode}")
    String getValue();

}
