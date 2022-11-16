package pl.training.shop.payments.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import pl.training.shop.commons.data.Page;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.commons.aop.Atomic;
import pl.training.shop.commons.aop.MinLength;
import pl.training.shop.payments.ports.PaymentRepository;
import pl.training.shop.payments.ports.PaymentService;
import pl.training.shop.payments.ports.TimeProvider;

@Atomic
@Log
@RequiredArgsConstructor
public class PaymentProcessor implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    private final TimeProvider timeProvider;

    @Override
    public PaymentDomain process(PaymentRequestDomain paymentRequest) {
        var paymentValue = calculatePaymentValue(paymentRequest.getValue());
        var payment = createPayment(paymentValue);
        return paymentsRepository.save(payment);
    }

    private PaymentDomain createPayment(Money paymentValue) {
        return PaymentDomain.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentValue)
                .timestamp(timeProvider.getTimestamp())
                .status(PaymentStatusDomain.STARTED)
                .build();
    }

    private Money calculatePaymentValue(Money paymentValue) {
        var paymentFee = paymentFeeCalculator.calculateFee(paymentValue);
        return paymentValue.add(paymentFee);
    }

    @Override
    public PaymentDomain getById(@MinLength String id) {
        return paymentsRepository.getById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public ResultPage<PaymentDomain> getByStatus(PaymentStatusDomain paymentStatusDomain, Page page) {
        return paymentsRepository.getByStatus(paymentStatusDomain, page);
    }

}
