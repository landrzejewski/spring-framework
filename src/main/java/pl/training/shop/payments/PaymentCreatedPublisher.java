package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

@Aspect
@RequiredArgsConstructor
public class PaymentCreatedPublisher /* implements ApplicationEventPublisherAware */ {

    private final ApplicationEventPublisher publisher;

    @AfterReturning(value = "@annotation(pl.training.shop.commons.aop.Loggable)", returning = "payment")
    public void onPaymentCreated(Payment payment) {
        publisher.publishEvent(payment);
    }

    /*@Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }*/

}
