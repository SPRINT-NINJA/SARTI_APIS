package mx.edu.utez.SARTI_APIS.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import mx.edu.utez.SARTI_APIS.kernel.ErrorMessages;
import mx.edu.utez.SARTI_APIS.kernel.ResponseApi;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomRestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi<?>> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        List<String> list = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseApi<?> res = new ResponseApi<>(
                list,
                HttpStatus.BAD_REQUEST,
                true,
                ErrorMessages.MULTIPLE_ERRORS.name()
        );

        return new ResponseEntity<>(res, res.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseApi<String>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String error = ex.getParameterName() + " parameter is missing";

        ResponseApi<String> apiError = new ResponseApi<>(
                error,
                HttpStatus.BAD_REQUEST,
                true,
                ErrorMessages.MISSING_PARAMETERS.name()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseApi<Map<String, Object>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        ResponseApi<Map<String, Object>> apiError = new ResponseApi<>(
                errors,
                HttpStatus.BAD_REQUEST,
                true,
                ErrorMessages.MULTIPLE_ERRORS.name()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseApi<String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        ResponseApi<String> apiError = new ResponseApi<>(
                error,
                HttpStatus.BAD_REQUEST,
                true,
                ex.getLocalizedMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseApi<String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ResponseApi<String> apiError = new ResponseApi<>(
                error,
                HttpStatus.NOT_FOUND,
                true,
                ex.getLocalizedMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseApi<String>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        ResponseApi<String> apiError = new ResponseApi<>(
                builder.toString(),
                HttpStatus.METHOD_NOT_ALLOWED,
                true,
                ex.getLocalizedMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseApi<String>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ResponseApi<String> apiError = new ResponseApi<>(
                builder.substring(0, builder.length() - 2),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                true,
                ex.getLocalizedMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ResponseApiException.class)
    public ResponseEntity<ResponseApi<String>> handleResponseApiException(ResponseApiException ex) {
        ResponseApi<String> apiError = new ResponseApi<>(
                ex.getMessage(),
                ex.getStatus(),
                true,
                ex.getMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<String>> handleAll(Exception ex) {
        ResponseApi<String> apiError = new ResponseApi<>(
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                true,
                ex.getLocalizedMessage()
        );

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
