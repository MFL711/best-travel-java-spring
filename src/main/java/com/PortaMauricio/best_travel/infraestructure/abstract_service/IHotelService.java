package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService <HotelResponse> {

    Set<HotelResponse> readByRating(Integer rating);


}
