package pl.training.shop.payments.adapters.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity(name = "Payment")
@EqualsAndHashCode(of = "id")
@Setter
@Getter
public class PaymentEntity {

    @Id
    private String id;
    private double value;
    private String currencyCode;
    private Instant timestamp;
    private String status;

}
