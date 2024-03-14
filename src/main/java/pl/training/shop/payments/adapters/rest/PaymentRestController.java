package pl.training.shop.payments.adapters.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.training.shop.commons.data.Page;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.commons.data.validation.Base;
import pl.training.shop.commons.web.ExceptionDto;
import pl.training.shop.commons.web.LocationUri;
import pl.training.shop.payments.domain.PaymentNotFoundException;
import pl.training.shop.payments.ports.PaymentService;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pl.training.shop.payments.domain.PaymentStatus.STARTED;

@RequestMapping("/api/payments")
@RestController
@RequiredArgsConstructor
// Pełni rolę adaptera, analogicznie jak na warstwie utrwalania
public class PaymentRestController {

    private final PaymentService paymentService;
    private final PaymentRestMapper mapper;

    @PostMapping
    public ResponseEntity<PaymentDto> process(@RequestBody /*@Valid*/ @Validated(Base.class) PaymentRequestDto paymentRequestDto) {
        var paymentRequest = mapper.toDomain(paymentRequestDto);
        var payment = paymentService.process(paymentRequest);
        var paymentDto = mapper.toDto(payment);
        var locationUri = LocationUri.fromRequestWith(paymentDto.getId());
        return ResponseEntity.created(locationUri)
                .body(paymentDto);
    }

    @GetMapping("{id:\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}}")
    public PaymentDto getById(@PathVariable String id) {
        var payment = paymentService.getById(id);
        return mapper.toDto(payment);
    }

    @GetMapping("started")
    public ResponseEntity<ResultPage<PaymentDto>> getStartedPayments(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        var resultPage = paymentService.getByStatus(STARTED, new Page(pageNumber, pageSize));
        var resultPageDto = mapper.toDto(resultPage);
        return ResponseEntity.ok(resultPageDto);
    }

   /*
   @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionDto> onPaymentNotFound(PaymentNotFoundException paymentNotFoundException) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ExceptionDto("Payment not found"));
    }
    */

}
