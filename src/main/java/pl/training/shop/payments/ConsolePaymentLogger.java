package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class ConsolePaymentLogger {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated";

    public void log(Payment payment) {
        log.info(LOG_FORMAT.formatted(payment.getValue()));
    }

}
