package com.PortaMauricio.best_travel.api.error_handler;

import com.PortaMauricio.best_travel.api.models.responses.BaseErrorResponse;
import com.PortaMauricio.best_travel.api.models.responses.ErrorResponse;
import com.PortaMauricio.best_travel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//status 400´s errores del lado del cliente
//status 500´s errores del lado del servidor
@RestControllerAdvice
@ResponseStatus (HttpStatus.BAD_REQUEST) //Cada que se accione este controlador va a responder con un
// bad request
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdFound (IdNotFoundException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
