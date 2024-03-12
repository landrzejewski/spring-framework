package pl.training.shop.payments.adapters.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaPaymentRepository extends /*Repository<PaymentEntity, String>*/ /*CrudRepository<PaymentEntity, String>*/
        JpaRepository<PaymentEntity, String> {

    Page<PaymentEntity> findByStatus(String status, Pageable pageable);

    @Query("select p from Payment p where p.status = :status")
    List<PaymentEntity> findAllByStatus(/*@Param("status")*/ String status);

}
