package pl.training.shop.payments.adapters.persistence.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoPaymentRepository extends MongoRepository<PaymentDocument, String> {

    Page<PaymentDocument> findByStatus(String status, Pageable pageable);

    @Query(value = "{value:  {$eq: ?0}}", fields = "{_id: 1, value: 1, currencyCode: 1}")
    Page<PaymentDocument> findByValue(double value, Pageable pageable);

}
