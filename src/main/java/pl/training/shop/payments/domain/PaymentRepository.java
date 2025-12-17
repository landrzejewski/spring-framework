package pl.training.shop.payments.domain;

import pl.training.shop.commons.data.PageDefinition;
import pl.training.shop.commons.data.ResultPage;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(String id);

    ResultPage<Payment> findByStatus(PaymentStatus status, PageDefinition pageDefinition);

}
