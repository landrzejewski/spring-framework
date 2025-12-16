package pl.training.shop.payments.adapters.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> /*Repository<PaymentEntity, String>*/ /*CrudRepository<PaymentEntity, String>*/ {

    Optional<PaymentEntity> findByStatus(String status);

    @Query("select p from Payment p where p.status = :status")
    Page<PaymentEntity> findAllByStatus(/*@Param("status")*/ String status, Pageable pageable);

}
