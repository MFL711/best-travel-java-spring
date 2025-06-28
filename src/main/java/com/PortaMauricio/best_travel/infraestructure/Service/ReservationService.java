package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.request.ReservationRequest;
import com.PortaMauricio.best_travel.api.models.responses.HotelResponse;
import com.PortaMauricio.best_travel.api.models.responses.ReservationResponse;
import com.PortaMauricio.best_travel.domain.entities.ReservationEntity;
import com.PortaMauricio.best_travel.domain.repositories.CustomerRepository;
import com.PortaMauricio.best_travel.domain.repositories.HotelRepository;
import com.PortaMauricio.best_travel.domain.repositories.ReservationRepository;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IReservationService;
import com.PortaMauricio.best_travel.infraestructure.helpers.ApiCurrencyConectorHelper;
import com.PortaMauricio.best_travel.infraestructure.helpers.BlockListHelper;
import com.PortaMauricio.best_travel.infraestructure.helpers.CustomerHelper;
import com.PortaMauricio.best_travel.util.enums.Tables;
import com.PortaMauricio.best_travel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
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
    private final ApiCurrencyConectorHelper apiCurrencyConectorHelper;


    @Override
    public ReservationResponse Create(ReservationRequest request) {
        //Validación del cliente para realizar la transacción
        blockListHelper.isInBlockListCustomer(request.getCustomerId());

        var hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(()-> new IdNotFoundException(
                        Tables.hotel.name()));
        var customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new IdNotFoundException(
                        Tables.customer.name()));

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
                reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(
                        Tables.reservation.name()));
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse Update(ReservationRequest request, UUID id) {
        //Obtenemos los objetos con los que trabajaremos de la base de datos
        var reservationToUpdate = reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(
                Tables.reservation.name()));
        var hotel = hotelRepository.findById(request.getHotelId()).orElseThrow(()-> new IdNotFoundException(
                Tables.hotel.name()));

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
                reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(
                        Tables.reservation.name()));
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
    public BigDecimal getHotelTotalPrice(Long hotelId, Currency currency) {
        // Buscar el hotel en la base de datos por su ID. Si no existe, lanza una excepción personalizada.
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
        // Calcula el precio total en dólares, sumando al precio base un porcentaje extra (cargos adicionales).
        var totalPriceDollars = hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));
        // Si la moneda solicitada es USD, devuelve directamente el precio calculado en dólares.
        if (currency.equals(Currency.getInstance("USD"))) return totalPriceDollars;
        // Se hace la consulta a la api y devuelve un objeto de tipo DTO
        var currencyDTO = this.apiCurrencyConectorHelper.getCurrency(currency);
        // Imprime en logs la fecha de la tasa de cambio obtenida y las tasas disponibles (útil para auditoría o debug).
        log.info("API currency {}, response: {}", currencyDTO.getExchangeDate().toString(), currencyDTO.getRates());
        // Devuelve el precio en la moneda solicitada, multiplicando el precio en dólares por la tasa de cambio correspondiente.
        return totalPriceDollars.multiply(currencyDTO.getRates().get(currency));
    }
}

