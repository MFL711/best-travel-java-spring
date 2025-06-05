package com.PortaMauricio.best_travel.infraestructure.helpers;

import com.PortaMauricio.best_travel.domain.entities.*;
import com.PortaMauricio.best_travel.domain.repositories.ReservationRepository;
import com.PortaMauricio.best_travel.domain.repositories.TicketRepository;
import com.PortaMauricio.best_travel.infraestructure.Service.ReservationService;
import com.PortaMauricio.best_travel.infraestructure.Service.TicketService;
import com.PortaMauricio.best_travel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Transactional
@Component //en lugar de poner service colocamos component
@AllArgsConstructor

/*Tour helper se encarga de la creación de tickets y reservaciones unicamente para tour*/
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    /*Explicacion del metodo
    * Lo que hace este metodo es que va a crear un ticket por cada vuelo que se agregue, por ello va a regresar un set
    * de entidades tipo ticket y recibe como parametros un set de vuelos y el customer
    *
    * Se recorre con un for each la lista de vuelos y por cada uno se crea un ticket, ticketRespositroy.save devuelve
    * una entidad como respuesta y esta se guarda y se agrega a la lista de response.*/
    public Set<TicketEntity> createTicket (Set<FlyEntity> flights, CustomerEntity customer){
        var response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(fly-> {
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.charges_price_percentage)))
                    .arrivalDate(BestTravelUtil.getArrivalHour())
                    .departureDate(BestTravelUtil.getDepartureHour())
                    .purchaseDate(LocalDate.now())
                    .build();
            response.add(this.ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations (HashMap<HotelEntity, Integer> hotels,
                                                  CustomerEntity customer){
        var response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            var reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .hotel(hotel)
                    .customer(customer)
                    .totalDays(totalDays)
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.charges_price_percentage)))
                    .build();
            response.add(this.reservationRepository.save(reservationToPersist));
        });

        return response;
    }

    //Este metodo ya está en nuestero tourService pero no podemos llamar un service dentro de un
    // service, es por oeso que creamos este metodo en la clase helper y serpa utilizado por el metodo
    // addTicket de la clase TourService
    public TicketEntity addFly(FlyEntity fly, CustomerEntity customer){
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.charges_price_percentage)))
                .arrivalDate(BestTravelUtil.getArrivalHour())
                .departureDate(BestTravelUtil.getDepartureHour())
                .purchaseDate(LocalDate.now())
                .build();
        return this.ticketRepository.save(ticketToPersist);

    }

    public ReservationEntity addHotel(HotelEntity hotel, CustomerEntity customer, Integer totalDays){
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .totalDays(totalDays)
                .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.charges_price_percentage)))
                .build();
        return this.reservationRepository.save(reservationToPersist);
    }
}
