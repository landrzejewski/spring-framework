package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.training.shop.commons.data.PageDefinition;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentRepository;
import pl.training.shop.payments.domain.PaymentStatus;

import java.util.Optional;

@Primary
@Transactional(propagation = Propagation.MANDATORY)
@Component
@RequiredArgsConstructor
public class JpaPaymentRepositoryAdapter implements PaymentRepository {

    private final JpaPaymentRepository repository;
    private final JpaPaymentModelMapper mapper;

    @Override
    public Payment save(Payment payment) {
        var paymentEntity = mapper.toEntity(payment);
        var persistedEntity = repository.save(paymentEntity);
        return mapper.toDomain(persistedEntity);
    }

    @Override
    public Optional<Payment> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public ResultPage<Payment> findByStatus(PaymentStatus status, PageDefinition pageDefinition) {
        var page = repository.findByStatus(status.name(), mapper.toEntity(pageDefinition));
        return mapper.toDomain(page);
    }

}
