package pl.training.shop.payments.adapters.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import pl.training.shop.commons.data.validation.Base;
import pl.training.shop.commons.data.validation.Extended;
import pl.training.shop.commons.data.validation.Money;

@Data
public class PaymentRequestDto {

    @Min(1)
    private Long id;
    @Pattern(regexp = "\\d+ [A-Z]{2,}", groups = {Base.class, Extended.class})
    @NotBlank(groups = Base.class)
    @Money(minValue = 10, groups = Extended.class)
    private String value;

}
