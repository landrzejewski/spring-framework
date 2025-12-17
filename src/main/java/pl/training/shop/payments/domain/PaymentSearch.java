package pl.training.shop.payments.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.training.shop.commons.aop.Atomic;
import pl.training.shop.commons.aop.MinLength;
import pl.training.shop.commons.data.PageDefinition;
import pl.training.shop.commons.data.ResultPage;

@Atomic
@Log
@RequiredArgsConstructor
public class PaymentSearch {

    private final PaymentRepository paymentsRepository;

    public Payment getById(@MinLength(16) String id) {
        return paymentsRepository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    public ResultPage<Payment> getByStatus(PaymentStatus status, PageDefinition pageDefinition) {
        return paymentsRepository.findByStatus(status, pageDefinition);
    }

}
