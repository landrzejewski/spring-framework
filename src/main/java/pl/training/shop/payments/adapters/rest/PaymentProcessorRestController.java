package pl.training.shop.payments.adapters.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.shop.commons.web.LocationUri;
import pl.training.shop.payments.domain.PaymentProcessor;

@RequestMapping("api/payments")
@RestController
@RequiredArgsConstructor
public class PaymentProcessorRestController {

    private final PaymentProcessor paymentProcessor;
    private final PaymentRestMapper mapper;

    @PostMapping
    public ResponseEntity<PaymentDto> process(@RequestBody PaymentRequestDto paymentRequestDto) {
        var paymentRequest = mapper.toDomain(paymentRequestDto);
        var payment = paymentProcessor.process(paymentRequest);
        var locationUri = LocationUri.fromRequestWith(payment.getId());
        return ResponseEntity.created(locationUri)
                .body(mapper.toDto(payment));
    }

}
