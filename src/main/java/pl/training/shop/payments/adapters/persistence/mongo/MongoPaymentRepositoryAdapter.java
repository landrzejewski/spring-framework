package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentRepository;

import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
@Component
@RequiredArgsConstructor
public class MongoPaymentRepositoryAdapter implements PaymentRepository {

    private final MongoPaymentRepository repository;
    private final MongoPaymentModelMapper mapper;

    @Override
    public Payment save(Payment payment) {
        var paymentDocument = mapper.toDocument(payment);
        var persistedDocument = repository.save(paymentDocument);
        return mapper.toDomain(persistedDocument);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

}
