package pl.training.shop.payments.adapters.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity(name = "PaymentProperty")
@Setter
@Getter
public class PaymentPropertyEntity {

    @GeneratedValue
    @Id
    private Long id;
    @Column(name = "property_value")
    private String value;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) {
            return false;
        }
        var that = (PaymentPropertyEntity) other;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
