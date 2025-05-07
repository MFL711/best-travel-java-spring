package com.PortaMauricio.best_travel.api.models.responses;

import com.PortaMauricio.best_travel.domain.entities.FlyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketResponse implements Serializable {

    private UUID id;
    private BigDecimal price;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalDate purchaseDate;
    private FlyResponse fly;


}
