package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.api.models.responses.HotelResponse;
import com.PortaMauricio.best_travel.domain.entities.HotelEntity;

import java.util.Set;

public interface IHotelService extends CatalogService <HotelResponse> {

    Set<HotelResponse> readGreaterThan(Integer rating);


}
