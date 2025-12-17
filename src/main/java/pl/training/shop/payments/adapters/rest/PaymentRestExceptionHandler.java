package pl.training.shop.payments.adapters.rest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.training.shop.commons.web.ExceptionDto;
import pl.training.shop.payments.domain.PaymentNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = PaymentRestExceptionHandler.class)
public class PaymentRestExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionDto> onPaymentNotFound(PaymentNotFoundException paymentNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto("Payment not found"));
    }

}
