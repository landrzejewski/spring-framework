package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Log
@RequiredArgsConstructor
public class ConsolePaymentLoggerAspect {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated";

    @AfterReturning(value = "bean(paymentProcessor)", returning = "payment")
    public void log(Payment payment) {
        log.info(LOG_FORMAT.formatted(payment.getValue()));
    }

}