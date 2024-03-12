package pl.training.shop.payments;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.javamoney.moneta.Money;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String id;
    private Money value;
    private Instant timestamp;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
