package com.PortaMauricio.best_travel.api.models.responses;

import com.PortaMauricio.best_travel.domain.entities.HotelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReservationResponse implements Serializable {

    private UUID id;
    private LocalDateTime dateTimeReservation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer totalDays;
    private BigDecimal price;
    private HotelEntity hotel;

}
