package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.request.ReservationRequest;
import com.PortaMauricio.best_travel.api.models.responses.HotelResponse;
import com.PortaMauricio.best_travel.api.models.responses.ReservationResponse;
import com.PortaMauricio.best_travel.domain.entities.ReservationEntity;
import com.PortaMauricio.best_travel.domain.repositories.CustomerRepository;
import com.PortaMauricio.best_travel.domain.repositories.HotelRepository;
import com.PortaMauricio.best_travel.domain.repositories.ReservationRepository;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IReservationService;
import com.PortaMauricio.best_travel.infraestructure.helpers.BlockListHelper;
import com.PortaMauricio.best_travel.infraestructure.helpers.CustomerHelper;
import com.PortaMauricio.best_travel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final CustomerHelper customerHelper;
    private final BlockListHelper blockListHelper;


    @Override
    public ReservationResponse Create(ReservationRequest request) {
        //Validación del cliente para realizar la transacción
        blockListHelper.isInBlockListCustomer(request.getCustomerId());

        var hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new IdNotFoundException("hotel"));
        var customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IdNotFoundException("customer"));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .totalDays(request.getTotalDays())
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                .build();
        var reservationPersisted = reservationRepository.save(reservationToPersist);

        this.customerHelper.increaseTourAndTicket(customer.getDni(), ReservationService.class);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse Read(UUID id) {
        var reservationFromDB =
                reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(
                        "reservation"));
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse Update(ReservationRequest request, UUID id) {
        //Obtenemos los objetos con los que trabajaremos de la base de datos
        var reservationToUpdate = reservationRepository.findById(id).orElseThrow(() ->
                new IdNotFoundException("reservation"));
        var hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(() ->
                new IdNotFoundException("hotel"));

        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateEnd(LocalDate.now());
        reservationToUpdate.setDateStart(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)));

        var reservationUpdated = reservationRepository.save(reservationToUpdate);
        log.info("Reservation Updated with id {}", reservationToUpdate.getId());
        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void Delete(UUID id) {
        var reservationToDelete =
                reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(
                        "reservation"));
        reservationRepository.delete(reservationToDelete);
    }

    private ReservationResponse entityToResponse (ReservationEntity entity){
        var response = new ReservationResponse();
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity, response);
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);

        response.setHotel(hotelResponse);
        return response;
    }

    /*Cramos una constate para el impuesto de los vuelos*/
    public static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.20);

    @Override
    public BigDecimal getHotelTotalPrice(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new IdNotFoundException("hotel"));
        return hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));
    }
}
