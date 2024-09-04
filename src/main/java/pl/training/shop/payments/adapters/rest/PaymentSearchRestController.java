package pl.training.shop.payments.adapters.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.shop.commons.data.ResultPage;
import pl.training.shop.payments.domain.PaymentSearch;

@RequestMapping("api/payments")
@RestController
@RequiredArgsConstructor
public class PaymentSearchRestController {

    private final PaymentSearch paymentSearch;
    private final PaymentRestMapper mapper;

    @GetMapping("{id:\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}}")
    public PaymentDto getPaymentById(@PathVariable String id) {
        var payment = paymentSearch.getById(id);
        return mapper.toDto(payment);
    }

    @GetMapping
    public ResponseEntity<ResultPage<PaymentDto>> getPayments(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String statusFilter) {
        var status = mapper.toDomain(statusFilter);
        var page = mapper.toDomain(pageNumber, pageSize);
        var result = paymentSearch.getByStatus(status, page);
        return ResponseEntity.ok(mapper.toDto(result));
    }

}
