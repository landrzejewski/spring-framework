package pl.training.shop.payments.adapters.rest;

import lombok.Data;
import org.javamoney.moneta.Money;
import pl.training.shop.payments.domain.PaymentStatus;

import java.time.Instant;

@Data
public class PaymentDto {

    private String id;
    private String value;
    private Instant timestamp;
    private String status;

}
