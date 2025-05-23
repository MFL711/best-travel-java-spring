package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService <FlyResponse>{

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
