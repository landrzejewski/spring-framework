package pl.training.shop;

import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.training.shop.payments.domain.PaymentRequest;
import pl.training.shop.payments.domain.PaymentService;

@Log
@SpringBootApplication
public class Application implements ApplicationRunner {

    private static final String DEFAULT_CURRENCY_CODE = "PLN";

    private final PaymentService paymentService;

    public Application(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var paymentRequest = new PaymentRequest(1L, Money.of(1_000, DEFAULT_CURRENCY_CODE));
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }

}
