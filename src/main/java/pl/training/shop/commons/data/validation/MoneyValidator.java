package pl.training.shop.commons.data.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.javamoney.moneta.FastMoney;

public class MoneyValidator implements ConstraintValidator<Money, String> {

    private double minValue;

    @Override
    public void initialize(Money money) {
        minValue = money.minValue();
    }

    @Override
    public boolean isValid(String text, ConstraintValidatorContext context) {
        boolean isValid;
        try {
            isValid = FastMoney.parse(text).isGreaterThanOrEqualTo(minValue);
        } catch (Exception e) {
            return false;
        }
        return isValid;
    }

}
