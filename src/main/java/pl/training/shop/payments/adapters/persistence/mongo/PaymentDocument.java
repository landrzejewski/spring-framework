package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "payments")
@EqualsAndHashCode(of = "id")
@Setter
@Getter
public class PaymentDocument {

    @Id
    private String id;
    private double value;
    private String currencyCode;
    private Instant timestamp;
    private String status;

}
