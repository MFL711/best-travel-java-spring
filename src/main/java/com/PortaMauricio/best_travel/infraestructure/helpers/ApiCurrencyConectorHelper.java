package com.PortaMauricio.best_travel.infraestructure.helpers;

import com.PortaMauricio.best_travel.infraestructure.dtos.CurrencyDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Currency;

@Component // Marca esta clase como un componente gestionado por Spring (se puede inyectar en otros beans)
public class ApiCurrencyConectorHelper {

    //Atributo que será leido desde el archivo api_currency.properties
    @Value(value = "${api.base-currency}")
    private String baseCurrency;
    // Cliente WebClient para hacer peticiones HTTP de manera reactiva
    private final WebClient currencyWebClient;

    // Constructor donde se inyecta el WebClient.
    public ApiCurrencyConectorHelper(@Qualifier(value = "currency")WebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    //Constantes de la URL de la API, URL completa https://api.apilayer.com/exchangerates_data/latest?base=USD&symbols=MXN, COP
    //https://api.apilayer.com URL base
    // /exchangerates_data/latest Path
    // ?base=USD Quey param
    // &symbols=MXN, COP, EUR Quey Param

    // Fragmentos constantes de la URL que se usarán para construir las peticiones
    private static final String BASE_CURRENCY_QUERY_PARAM = "?base={base}"; // Parámetro de consulta para la moneda base
    private static final String SYMBOL_CURRENCY_QUERY_PARAM = "&symbols={symbol}"; // Parámetro de consulta para la moneda de destino
    private static final String CURRENCY_PATH = "/exchangerates_data/latest";  // Path de la API para obtener los tipos de cambio

    // Metodo que realiza la llamada a la API para obtener el tipo de cambio de una moneda específica
    public CurrencyDTO getCurrency (Currency currency){
        return this.currencyWebClient
                .get() // Indica que se hará una petición GET
                .uri (uri ->
                        uri.path(CURRENCY_PATH) // Define el path de la API
                                .query(BASE_CURRENCY_QUERY_PARAM) // Agrega parámetro de la moneda base
                                .query(SYMBOL_CURRENCY_QUERY_PARAM) // Agrega parámetro de la moneda base
                                .build(baseCurrency,currency.getCurrencyCode())) // Sustituye {base} y {symbol} por sus valores reales
                .retrieve() // Ejecuta la petición y prepara la respuesta
                .bodyToMono(CurrencyDTO.class) // Convierte la respuesta JSON en un objeto CurrencyDTO de manera reactiva
                .block(); // Hace que la operación sea sincrónica, esperando el resultado
    }
}
