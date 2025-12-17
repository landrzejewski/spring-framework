package pl.training.shop.commons.data.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static java.util.stream.Collectors.joining;

@Component
public class ValidationExceptionMapper {

    String KEY_VALUE_SEPARATOR = " ";
    String DELIMITER = ", ";

    public String getValidationErrors(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + KEY_VALUE_SEPARATOR + fieldError.getDefaultMessage())
                .collect(joining(DELIMITER));
    }

}
