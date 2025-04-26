package crosso.workshop.schools_api.controller.advice;

import crosso.workshop.schools_api.exception.EntityNotFoundException;
import crosso.workshop.schools_api.utils.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException exception, HttpServletRequest httpServletRequest) {
        ErrorResponse error = new ErrorResponse();
        error.setDescription(exception.getMessage());
        error.setErrorCode(exception.getError());
        error.setURI(httpServletRequest.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
