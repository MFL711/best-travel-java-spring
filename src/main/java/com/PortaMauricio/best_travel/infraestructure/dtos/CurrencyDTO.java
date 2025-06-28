package com.PortaMauricio.best_travel.infraestructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Data
public class CurrencyDTO implements Serializable {

    @JsonProperty(value = "date")
    private LocalDate exchangeDate; //el dato original que entrega la api se llama date, tendriamos
    // problemas al intentar mapear este dato si no usamos la libreria JacksonDataBean

    //Currency es un enumerador que contiene todos los codigos iso de todos o muchos paises
    private Map<Currency, BigDecimal> rates;

}
