package pl.training.shop.payments;

import org.springframework.context.annotation.Primary;
import pl.training.shop.commons.Generator;

import java.util.UUID;

@Primary
@Generator
public class UuidPaymentIdGenerator implements PaymentIdGenerator {

    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }

}
