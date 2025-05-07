package com.PortaMauricio.best_travel.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketRequest implements Serializable{

    /*Cuando el cliente haga un request necesitaremos dos datos o uno de estos dos datos.*/
    private String idCliente;
    private Long idFly;
}
