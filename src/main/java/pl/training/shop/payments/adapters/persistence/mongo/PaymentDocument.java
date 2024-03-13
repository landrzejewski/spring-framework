package pl.training.shop.payments.adapters.persistence.mongo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.training.shop.payments.adapters.persistence.jpa.PaymentPropertyEntity;

import java.time.Instant;
import java.util.List;

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
    private List<PaymentPropertyEntity> properties;

    @Override
    public String toString() {
        return "PaymentEntity{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }

}
