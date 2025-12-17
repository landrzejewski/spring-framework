package pl.training.shop.payments.adapters.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPaymentRepository extends MongoRepository<PaymentDocument, String> {
}
