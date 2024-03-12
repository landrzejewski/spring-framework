package pl.training.shop.payments.adapters.persistence.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static pl.training.shop.Application.DEFAULT_CURRENCY_CODE;

@Transactional
@Component
@Log
@RequiredArgsConstructor
public class JpaExamples implements ApplicationRunner {

    private final JpaPaymentRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("### JPA Examples");
        log.info(repository.findByCurrencyCode(DEFAULT_CURRENCY_CODE).toString());
        log.info("###");
    }

}
