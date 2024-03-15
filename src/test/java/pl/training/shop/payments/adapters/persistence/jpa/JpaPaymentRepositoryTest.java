package pl.training.shop.payments.adapters.persistence.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static pl.training.shop.payments.PaymentsFixtures.createEntity;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class JpaPaymentRepositoryTest {

    @Autowired
    private JpaPaymentRepository repository;
    @Autowired
    private EntityManager entityManager;

    private PaymentEntity initDatabase(String status) {
        var paymentEntity = createEntity(status);
        entityManager.persist(paymentEntity);
        entityManager.flush();
        return paymentEntity;
    }

    @Test
    void given_completed_payment_in_database_when_query_by_status_and_value_then_returns_the_payment() {
        var paymentEntity = initDatabase("COMPLETED");
        var result = repository.findCompletedWithValue(paymentEntity.getValue(), PageRequest.of(0, 1));
        var resultEntity = result.getContent().getFirst();
        assertEquals(paymentEntity, resultEntity);
    }

    @Test
    void given_there_is_no_completed_payments_in_database_when_find_by_status_and_value_then_returns_empty_list() {
        var paymentEntity = initDatabase("STARTED");
        var result = repository.findCompletedWithValue(paymentEntity.getValue(), PageRequest.of(0, 1));
        assertTrue(result.getContent().isEmpty());
    }

}
