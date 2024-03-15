package pl.training.shop.payments.domain;

import lombok.Setter;
import org.javamoney.moneta.Money;

public class PercentagePaymentFeeCalculator implements PaymentFeeCalculator {

    public static final double DEFAULT_MAX_PERCENTAGE = 0.5;

    private final double percentage;

    @Setter
    private double maxPercentage = DEFAULT_MAX_PERCENTAGE;

    public PercentagePaymentFeeCalculator(double percentage) {
        if (percentage > maxPercentage) {
            throw new IllegalStateException();
        }
        this.percentage = percentage;
    }

    @Override
    public Money calculateFee(Money paymentValue) {
        return paymentValue.multiply(percentage);
    }

}
