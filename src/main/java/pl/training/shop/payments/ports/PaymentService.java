package pl.training.shop.payments.ports;

import pl.training.shop.payments.domain.Payment;
import pl.training.shop.payments.domain.PaymentRequest;

public interface PaymentService {

    Payment process(PaymentRequest paymentRequest);

    Payment getById(String id);

}
