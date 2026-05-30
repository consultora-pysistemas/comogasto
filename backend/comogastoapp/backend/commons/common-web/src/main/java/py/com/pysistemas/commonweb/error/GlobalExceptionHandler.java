package py.com.pysistemas.commonweb.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import jakarta.validation.ConstraintViolationException;
import py.com.pysistemas.commonweb.exception.BusinessException;
import py.com.pysistemas.commonweb.exception.ConflictException;
import py.com.pysistemas.commonweb.exception.ResourceNotFoundException;

/*
 * @author josec
 * @project comogastoapp
 */
@RestControllerAdvice
@SuppressWarnings({"java:S5131", "squid:S5131"})
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorEnvelope> handleValidation(
            MethodArgumentNotValidException exception
    ) {
        List<FieldErrorItem> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorItem(
                        sanitize(error.getField()),
                        sanitize(error.getDefaultMessage()),
                        null
                ))
                .toList();

        ErrorEnvelope envelope = ErrorEnvelope.of(
                ErrorCode.VALIDATION_ERROR,
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                null,
                null,
                errors
        );

        return ResponseEntity.badRequest().body(envelope);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorEnvelope> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        List<FieldErrorItem> errors = exception.getConstraintViolations()
                .stream()
                .map(error -> new FieldErrorItem(
                        sanitize(error.getPropertyPath().toString()),
                        sanitize(error.getMessage()),
                        null
                ))
                .toList();

        ErrorEnvelope envelope = ErrorEnvelope.of(
                ErrorCode.VALIDATION_ERROR,
                "Constraint validation error",
                HttpStatus.BAD_REQUEST.value(),
                null,
                null,
                errors
        );

        return ResponseEntity.badRequest().body(envelope);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorEnvelope> handleBusiness(
            BusinessException exception
    ) {
        ErrorEnvelope envelope = ErrorEnvelope.of(
                exception.getCode(),
                "Business rule violation",
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                null,
                null
        );

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(envelope);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorEnvelope> handleNotFound(
            ResourceNotFoundException exception
    ) {
        ErrorEnvelope envelope = ErrorEnvelope.of(
                exception.getCode(),
                "Resource not found",
                HttpStatus.NOT_FOUND.value(),
                null,
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(envelope);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorEnvelope> handleConflict(
            ConflictException exception
    ) {
        ErrorEnvelope envelope = ErrorEnvelope.of(
                exception.getCode(),
                "Resource conflict",
                HttpStatus.CONFLICT.value(),
                null,
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(envelope);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEnvelope> handleGeneric(
            Exception exception
    ) {
        LOGGER.error("Unhandled internal exception", exception);

        ErrorEnvelope envelope = ErrorEnvelope.of(
                ErrorCode.INTERNAL_ERROR,
                "Unexpected internal error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null,
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(envelope);
    }

    private static String sanitize(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

}
