package pl.training.shop.payments;

import pl.training.shop.commons.Generator;

@Generator
public class FakePaymentIdGenerator implements PaymentIdGenerator {

    private static final String ID = "1";

    @Override
    public String getNext() {
        return ID;
    }

}
