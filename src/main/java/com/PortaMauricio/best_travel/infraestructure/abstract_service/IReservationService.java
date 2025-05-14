package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.api.models.request.ReservationRequest;
import com.PortaMauricio.best_travel.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService <ReservationRequest, ReservationResponse, UUID>{
    BigDecimal getHotelTotalPrice(Long hotelId);

}
