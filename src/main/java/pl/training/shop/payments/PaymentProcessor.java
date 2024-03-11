package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.training.shop.commons.Generator;
import pl.training.shop.time.TimeProvider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Log
public class PaymentProcessor implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentFeeCalculator paymentFeeCalculator;
    private final PaymentRepository paymentsRepository;
    // @Autowired // zła praktyka
    private TimeProvider timeProvider;

    @Autowired // oznacza konieczność wstrzyknięcia zależności, jeżeli istnieje tylko jeden konstruktor z parametrami, to Spring uznaje go jako domyślny i z jego użyciem tworzy instancję
    // (w tym przypadku @Autowired nie jest wymagane, ale jest wskazane - jeżli nie użyjemy @Autowire to Spring wybierze konstruktor bezargumentowy)
    public PaymentProcessor(
            PaymentIdGenerator paymentIdGenerator,
            // @Qualifier("fakePaymentIdGenerator") PaymentIdGenerator paymentIdGenerator, // kwalifikacja z użyciem nazwy i adnotacji @Qualifier
            // @Generator("fakePaymentIdGenerator") PaymentIdGenerator paymentIdGenerator, // kwalifikacja z użyciem nazwy i adnotacji niestandardowej
            // PaymentIdGenerator fakePaymentIdGenerator, // kwalifikacja przez nazwę argumentu/beana
            // PaymentIdGenerator paymentIdGenerator, // kwalifikacja przez wybór implementacji adnotacją @Primary
            PaymentFeeCalculator paymentFeeCalculator,
            PaymentRepository paymentsRepository
           /* TimeProvider timeProvider*/) {
        this.paymentIdGenerator = paymentIdGenerator;
        this.paymentFeeCalculator = paymentFeeCalculator;
        this.paymentsRepository = paymentsRepository;
     //   this.timeProvider = timeProvider;
    }

    @Autowired
    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var paymentValue = calculatePaymentValue(paymentRequest.getValue());
        var payment = createPayment(paymentValue);
        return paymentsRepository.save(payment);
    }

    private Payment createPayment(Money paymentValue) {
        return Payment.builder()
                .id(paymentIdGenerator.getNext())
                .value(paymentValue)
                .timestamp(timeProvider.getTimestamp())
                .status(PaymentStatus.STARTED)
                .build();
    }

    private Money calculatePaymentValue(Money paymentValue) {
        var paymentFee = paymentFeeCalculator.calculateFee(paymentValue);
        return paymentValue.add(paymentFee);
    }

    // Wymagania dla metod związanych z cyklem życia
    // - brak argumentów
    // - brak resultatu
    // - brak wyjątków typu Exception

    // Metoda do inicjalizaji - wołana po wstrzyknięciu wszystkich zależności
    @PostConstruct
    public void init() {
        log.info("Initializing Payment processor");
    }

    // Metoda do sprzątania - wołana po przed zniszczeniem beana (zwolnieniem referencji), działa tylko dal scope SINGLETON i przy prawidłowym zatrzymaniu kontenera
    @PreDestroy
    public void destroy() {
        log.info("Destroying Payment processor");
    }

}
