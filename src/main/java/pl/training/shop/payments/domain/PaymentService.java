package pl.training.shop.payments.domain;

public interface PaymentService {

    Payment process(PaymentRequest paymentRequest);

    Payment getById(String id);

}
