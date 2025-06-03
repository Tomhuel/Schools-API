package crosso.workshop.schools_api.controller.advice;

import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.utils.response.ErrorResponse;
import crosso.workshop.schools_api.utils.response.field.ErrorField;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException exception, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse();
        error.setErrors(Collections.singletonList(new ErrorField(exception.getError(), exception.getMessage(), exception.getIdNotFound())));
        error.setUri(httpServletRequest.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationError(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse();

        var errors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            String causedBy = String.format("%s.%s", fieldError.getObjectName(), fieldError.getField());
            String description = fieldError.getDefaultMessage();
            return new ErrorField(causedBy, description, String.valueOf(fieldError.getRejectedValue()));
        }).toList();

        error.setErrors(errors);
        error.setUri(httpServletRequest.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> propertyReferenceError(PropertyReferenceException e, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse();

        var errors = new ArrayList<ErrorField>();

        String causedBy = String.format("%s.%s", e.getType().getType().getSimpleName(), e.getPropertyName());

        errors.add(new ErrorField(causedBy, e.getMessage(), e.getPropertyName()));

        error.setUri(httpServletRequest.getRequestURI());
        error.setErrors(errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
