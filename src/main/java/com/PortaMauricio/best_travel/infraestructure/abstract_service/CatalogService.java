package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.util.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

//Es parecido a la interfaz CRUD y en este caso solo trabajaremos con un tipo de dato <R>, que es un response
public interface CatalogService <R>{

    /**
     * @Mau
     * Este metodo necesita tres parámetros, un integer para saber que página va a regresar, el tamaño de esa página
     * y el tipo de ordenamiento que tendrá esa pagina.
     */
    Page <R> readAll (Integer page, Integer size, SortType sortType);

    Set <R> readLessPrice (BigDecimal price);

    Set <R> readBetweenPrice (BigDecimal min, BigDecimal max);

    String FIELD_BY_SORT = "price"; /*Es el parámetro por el cual se ordenarán nuestras páginas y esta interfaz será
    usada por la entidad fly y hotel, ambas tienen y serán ordenadas sus consultas por el precio.*/
}
