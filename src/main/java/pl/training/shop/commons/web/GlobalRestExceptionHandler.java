package pl.training.shop.commons.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pl.training.shop.commons.data.validation.ValidationExceptionMapper;

import java.util.Locale;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log
@ControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class GlobalRestExceptionHandler {

    private final RestExceptionResponseBuilder responseBuilder;
    private final ValidationExceptionMapper validationExceptionMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> onException(Exception exception, Locale locale) {
        log.info("Exception: " + exception.getClass().getName());
        return responseBuilder.build(exception, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> onMethodArgumentNotValid(MethodArgumentNotValidException exception, Locale locale) {
        var errors = validationExceptionMapper.getValidationErrors(exception);
        var description = responseBuilder.getLocalizedMessage(exception, locale, errors);
        return responseBuilder.build(description, BAD_REQUEST);
    }

}
