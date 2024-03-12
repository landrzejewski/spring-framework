package pl.training.shop.payments.adapters.persistence.jpa;

import java.util.List;

public interface JpaPaymentRepositoryExtensions {

    List<PaymentEntity> findByCurrencyCode(String code);

}
