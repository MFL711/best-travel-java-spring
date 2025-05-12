package com.PortaMauricio.best_travel.api.models.request;

import com.PortaMauricio.best_travel.domain.entities.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest implements Serializable {

    private String customer;
    private Integer hotelId;
}
