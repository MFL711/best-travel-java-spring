package com.PortaMauricio.best_travel.api.error_handler;

import com.PortaMauricio.best_travel.api.models.responses.BaseErrorResponse;
import com.PortaMauricio.best_travel.api.models.responses.ErrorResponse;
import com.PortaMauricio.best_travel.api.models.responses.ErrorsResponse;
import com.PortaMauricio.best_travel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

//status 400´s errores del lado del cliente
//status 500´s errores del lado del servidor
@RestControllerAdvice
@ResponseStatus (HttpStatus.BAD_REQUEST) //Cada que se accione este controlador va a responder con un
// bad request
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdFound (IdNotFoundException exception){
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    //MethodArgumentNotValidException es un error de spring y nos ayudará a identificar cuando un
    // usuario haya ingresado un dato de forma errónea. La clase de error que usa el Exception Handler
    // siempre es la misma que se debe de colocar como argumento del metodo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleIdFound(MethodArgumentNotValidException exception) {
        var errors = new ArrayList<String>(); //En esta lista guardaremos todos los mensajes de error
        //MethodArgumentNotValidException tiene un metodo para obtener todos los errores.
        exception.getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));
        //Construimos el objeto de tipo ErrorsResponse que creamos para este tipo de errores
        return ErrorsResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
