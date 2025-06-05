package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.api.models.request.TourRequest;
import com.PortaMauricio.best_travel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudService <TourRequest, TourResponse, Long>{

    //Borrar un ticket del tour, para esto necesitamos el id del ticket y el id de tour
    void removeTicket(Long tourId, UUID ticketId);

    //Para añadir un vuelo, necesitamos el id del vuelo y el id de tour, regresa el UUID del ticket donde se agrego
    // el vuelo*/
    UUID addTicket (Long tourId, Long flyId);

    //Borrar una reservacion del tour, para esto necesitamos el id de la reservación y el id de tour
    void removeReservation(Long tourId, UUID reservationId);

    //Para añadir una reservación (hotel), necesitamos el id del hotel y el id del tour
    UUID addReservation (Long tourId, Long hotelId, Integer totalDays);



}
