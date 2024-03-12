package pl.training.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.training.shop.payments.domain.PaymentRequest;
import pl.training.shop.payments.ports.PaymentService;

// java -jar -Dspring.profiles.active=dev  shop-1.0-SNAPSHOT.jar

@Log
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements ApplicationRunner {

    private static final String DEFAULT_CURRENCY_CODE = "PLN";

    private final PaymentService paymentService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var paymentRequest = new PaymentRequest(1L, Money.of(1_000, DEFAULT_CURRENCY_CODE));
        var payment = paymentService.process(paymentRequest);
        log.info(payment.toString());
    }

}
