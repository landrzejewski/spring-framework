package pl.training.shop.payments.adapters.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Setter;

import java.util.List;

import static pl.training.shop.payments.adapters.persistence.jpa.PaymentEntity.FIND_WITH_CURRENCY_CODE;

public class JpaPaymentRepositoryImpl implements JpaPaymentRepositoryExtensions {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentEntity> findByCurrencyCode(String code) {
        return entityManager.createNamedQuery(FIND_WITH_CURRENCY_CODE, PaymentEntity.class)
                .setParameter("code", code)
                .getResultList();
    }

}
