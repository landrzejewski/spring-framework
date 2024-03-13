package pl.training.shop.payments.adapters.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.training.shop.commons.web.ExceptionDto;
import pl.training.shop.commons.web.RestExceptionResponseBuilder;
import pl.training.shop.payments.domain.PaymentNotFoundException;

import java.util.Locale;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(HIGHEST_PRECEDENCE)
@RestControllerAdvice("pl.training.shop.payments.adapters.rest")
@RequiredArgsConstructor
public class PaymentRestExceptionHandler {

    private final RestExceptionResponseBuilder responseBuilder;

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionDto> onPaymentNotFound(PaymentNotFoundException paymentNotFoundException, Locale locale) {
        return responseBuilder.build(paymentNotFoundException, NOT_FOUND, locale);
    }

}
