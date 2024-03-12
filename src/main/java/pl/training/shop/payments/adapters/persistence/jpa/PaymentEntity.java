package pl.training.shop.payments.adapters.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NamedQuery(name = PaymentEntity.FIND_WITH_CURRENCY_CODE, query = "select p from Payment p where p.currencyCode = :code")
@Entity(name = "Payment")
@EqualsAndHashCode(of = "id")
@Setter
@Getter
@ToString
public class PaymentEntity {

    public static final String FIND_WITH_CURRENCY_CODE = "PaymentEntity.FIND_WITH_CURRENCY_CODE";

    @Id
    private String id;
    @Column(name = "amount")
    private double value;
    private String currencyCode;
    private Instant timestamp;
    private String status;

}
