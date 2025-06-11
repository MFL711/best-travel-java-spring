package com.PortaMauricio.best_travel.util.exceptions;

public class IdNotFoundException extends RuntimeException{

    //Constante
    private static final String ERROR_MESSAGE = "Record no exist in %s";

    //Constructor de la excepcion
    public IdNotFoundException(String table){
        super(String.format(ERROR_MESSAGE, table));
    }
}
