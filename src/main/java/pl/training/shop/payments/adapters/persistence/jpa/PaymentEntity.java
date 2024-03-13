package pl.training.shop.payments.adapters.persistence.jpa;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@NamedEntityGraph(name = PaymentEntity.WITH_PROPERTIES, attributeNodes = @NamedAttributeNode("properties"))
@NamedQuery(name = PaymentEntity.FIND_WITH_CURRENCY_CODE, query = "select p from Payment p where p.currencyCode = :code")
@Entity(name = "Payment")
@EqualsAndHashCode(of = "id")
@Setter
@Getter
public class PaymentEntity {

    public static final String FIND_WITH_CURRENCY_CODE = "PaymentEntity.FIND_WITH_CURRENCY_CODE";
    public static final String WITH_PROPERTIES = "PaymentEntity.WITH_PROPERTIES";

    @Id
    private String id;
    @Column(name = "amount")
    private double value;
    private String currencyCode;
    private Instant timestamp;
    private String status;
    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
