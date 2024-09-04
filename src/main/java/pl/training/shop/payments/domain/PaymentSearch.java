package pl.training.shop.payments.domain;

import lombok.RequiredArgsConstructor;
import pl.training.shop.commons.aop.Atomic;
import pl.training.shop.commons.aop.MinLength;
import pl.training.shop.commons.data.Page;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.payments.ports.PaymentRepository;

@Atomic
@RequiredArgsConstructor
public class PaymentSearch {

    private final PaymentRepository paymentsRepository;

    public Payment getById(@MinLength(16) String id) {
        return paymentsRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    public ResultPage<Payment> getByStatus(PaymentStatus status, Page page) {
        return paymentsRepository.findByStatus(status, page);
    }

}
