package com.PortaMauricio.best_travel.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourResponse implements Serializable {

    private Long id; //Respondemos con el id del tour
    private Set<UUID> ticketIds; //Respondemos con los ids de los tickets
    private Set<UUID> reservationIds;
}
