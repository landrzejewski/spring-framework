package pl.training.shop.payments.adapters.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.shop.commons.data.validation.Base;
import pl.training.shop.commons.web.LocationUri;
import pl.training.shop.payments.domain.PaymentProcessor;

@RequestMapping("api/payments")
@RestController
@RequiredArgsConstructor
public class PaymentProcessorRestController {

    private final PaymentProcessor paymentProcessor;
    private final RestPaymentModelMapper mapper;


    // @Secured("ROLE_MANAGER")
    // @RolesAllowed("MANAGER")
    // @PreAuthorize("#paymentRequestDto.id > 100")
    // @PostAuthorize("returnObject.statusCode != 201")
    // @PreFilter("filterObject.owner == authentication.name")
    // @PostFilter("filterObject.owner == authentication.name")
    @PostMapping
    public ResponseEntity<PaymentDto> process(/*@Valid*/ @Validated(Base.class) @RequestBody PaymentRequestDto paymentRequestDto) {
        var paymentRequest = mapper.toDomain(paymentRequestDto);
        var payment = paymentProcessor.process(paymentRequest);
        var locationUri = LocationUri.fromRequestWith(payment.getId());
        return ResponseEntity.created(locationUri)
                .body(mapper.toDto(payment));
    }

}
