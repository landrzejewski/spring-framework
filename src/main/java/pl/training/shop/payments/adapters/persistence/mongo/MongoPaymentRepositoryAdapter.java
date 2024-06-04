package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.payments.adapters.persistence.jpa.JpaPaymentRepository;
import pl.training.shop.payments.adapters.persistence.jpa.JpaPaymentRepositoryMapper;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.ports.PaymentRepository;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Primary
@Transactional(propagation = MANDATORY)
@Component
@RequiredArgsConstructor
public class MongoPaymentRepositoryAdapter implements PaymentRepository {

    private final MongoPaymentRepositoryMapper mapper;
    private final MongoPaymentRepository repository;

    @Override
    public Payment save(Payment payment) {
        var paymentDocument = mapper.toDocument(payment);
        return mapper.toDomain(repository.save(paymentDocument));
    }

    @Override
    public Optional<Payment> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

}
