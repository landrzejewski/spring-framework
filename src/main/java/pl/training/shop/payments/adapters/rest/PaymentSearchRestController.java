package pl.training.shop.payments.adapters.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.shop.commons.data.PageDefinition;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.commons.web.ExceptionDto;
import pl.training.shop.payments.domain.PaymentNotFoundException;
import pl.training.shop.payments.domain.PaymentSearch;

@RequestMapping("api/payments")
@RestController
@RequiredArgsConstructor
public class PaymentSearchRestController {

    private final PaymentSearch paymentSearch;
    private final RestPaymentModelMapper mapper;

    @GetMapping("{id:\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}}")
    public ResponseEntity<PaymentDto> getById(@PathVariable String id) {
        var payment = paymentSearch.getById(id);
        return ResponseEntity.ok(mapper.toDto(payment));
    }

    @GetMapping
    public ResponseEntity<ResultPage<PaymentDto>> searchPayments(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam String status) {
        var resultPage = paymentSearch.getByStatus(mapper.toDomain(status), new PageDefinition(pageNumber, pageSize))
                .map(mapper::toDto);
        return ResponseEntity.ok(resultPage);
    }

   /*@ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionDto> onPaymentNotFound(PaymentNotFoundException paymentNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto("Payment not found"));
    }*/

}
