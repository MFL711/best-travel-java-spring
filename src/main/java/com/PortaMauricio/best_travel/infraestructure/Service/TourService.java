package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.request.TourRequest;
import com.PortaMauricio.best_travel.api.models.responses.TourResponse;
import com.PortaMauricio.best_travel.domain.entities.*;
import com.PortaMauricio.best_travel.domain.repositories.*;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITourService;
import com.PortaMauricio.best_travel.infraestructure.helpers.BlockListHelper;
import com.PortaMauricio.best_travel.infraestructure.helpers.CustomerHelper;
import com.PortaMauricio.best_travel.infraestructure.helpers.TourHelper;
import com.PortaMauricio.best_travel.util.enums.Tables;
import com.PortaMauricio.best_travel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {

    private final CustomerRepository customerRepository;
    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper; //Para actualizer los contadores de cuantos tickets,
    // reservaciones y tours tiene cada cliente
    private final BlockListHelper blockListHelper;

    /*No es buena practica que dentro de un service mandemos a llamar a otro service
     * para esto crearemos una clase helper*/

    @Override
    public TourResponse create(TourRequest request) {
        //Validación del cliente para realizar transacción
        blockListHelper.isInBlockListCustomer(request.getCustomerId());
        var customer =
                customerRepository.findById(request.getCustomerId()).orElseThrow(()-> new IdNotFoundException(
                        Tables.customer.name()));
        var flights = new HashSet<FlyEntity>(); //HashSet vació que guardará los vuelos
        var hotels = new HashMap<HotelEntity, Integer>(); //El integer es el numero total de dias de la reservación
        /*Recorremos la lista de vuelos del request y los vamos añadiendo a nuestro hash flights, primero buscamos
         * el vuelo y guardamos el resultado de la búsqueda.*/
        request.getFlights()
                .forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow(()-> new IdNotFoundException(Tables.fly.name()))));
        request.getHotels()
                .forEach(hotel -> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(()-> new IdNotFoundException(Tables.fly.name())),hotel.getTotalDays()));
        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTicket(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();

        var tourSaved = this.tourRepository.save(tourToSave);

        this.customerHelper.increaseTourAndTicket(customer.getDni(), TourService.class);

        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(
                        Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId)
                        .collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDb = this.tourRepository.findById(id).orElseThrow(()-> new IdNotFoundException(
                Tables.tour.name()));

        return TourResponse.builder()
                .reservationIds(tourFromDb.getReservations().stream().map(ReservationEntity::getId).collect(
                        Collectors.toSet()))
                .ticketIds(tourFromDb.getTickets().stream().map(TicketEntity::getId)
                        .collect(Collectors.toSet()))
                .id(tourFromDb.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        this.tourRepository.deleteById(id);
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId) {
        var  tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(
                Tables.tour.name()));//Encontramos el tour
        tourUpdate.removeTicket(ticketId);//Eliminamos el vuelo que nos pasó el cliente
        this.tourRepository.save(tourUpdate); //Guardamos la actualización en la base de datos
    }

    @Override
    public UUID addTicket(Long tourId, Long flyId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(
                Tables.tour.name()));
        var fly = this.flyRepository.findById(flyId).orElseThrow(()-> new IdNotFoundException(
                Tables.fly.name()));
        var ticket = this.tourHelper.addFly(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(
                Tables.tour.name()));
        tourToUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourToUpdate);
        }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException(
                Tables.tour.name()));
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException(
                Tables.hotel.name()));
        var reservation = this.tourHelper.addHotel(hotel, tourToUpdate.getCustomer(), totalDays);
        tourToUpdate.addReservation(reservation);
        this.tourRepository.save(tourToUpdate);
        return reservation.getId();
    }
}
