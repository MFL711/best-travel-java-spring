package com.PortaMauricio.best_travel.api.error_handler;

import com.PortaMauricio.best_travel.api.models.responses.BaseErrorResponse;
import com.PortaMauricio.best_travel.api.models.responses.ErrorResponse;
import com.PortaMauricio.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN) //Cada que se accione este controlador va a responder con un
public class ForbiddenCustomerHandler {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse customerBlocked (ForbiddenCustomerException exception){
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
