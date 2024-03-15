package pl.training.shop.payments.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.training.shop.payments.PaymentsFixtures.PAYMENT_VALUE;
import static pl.training.shop.payments.domain.PercentagePaymentFeeCalculator.DEFAULT_MAX_PERCENTAGE;

class PercentagePaymentFeeCalculatorTest {

    private static final double PERCENTAGE_VALUE = 0.1;

    @Test
    void given_payment_value_when_calculate_fee_the_returns_percentage_of_the_payment_value() {
        var calculator = new PercentagePaymentFeeCalculator(PERCENTAGE_VALUE);
        var fee = calculator.calculateFee(PAYMENT_VALUE);
        assertEquals(PAYMENT_VALUE.multiply(PERCENTAGE_VALUE), fee);
    }

    @Test
    void given_percentage_value_greater_then_max_percentage_value_when_create_calculator_throws_exception() {
        assertThrows(IllegalStateException.class, () -> new PercentagePaymentFeeCalculator(DEFAULT_MAX_PERCENTAGE  + 1));
    }

}
