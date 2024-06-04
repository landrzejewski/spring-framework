package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.training.shop.payments.domain.PaymentStatus;

import java.time.Instant;

@Document(collection = "payments")
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class PaymentDocument {

    @Id
    private String id;
    private double value;
    private String currencyCode;
    private Instant timestamp;
    private String status;

}
