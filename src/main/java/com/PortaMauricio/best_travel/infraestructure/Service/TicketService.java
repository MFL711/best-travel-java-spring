package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.request.TicketRequest;
import com.PortaMauricio.best_travel.api.models.responses.FlyResponse;
import com.PortaMauricio.best_travel.api.models.responses.TicketResponse;
import com.PortaMauricio.best_travel.domain.entities.TicketEntity;
import com.PortaMauricio.best_travel.domain.repositories.CustomerRepository;
import com.PortaMauricio.best_travel.domain.repositories.FlyRepository;
import com.PortaMauricio.best_travel.domain.repositories.TicketRepository;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITicketService;
import com.PortaMauricio.best_travel.infraestructure.helpers.CustomerHelper;
import com.PortaMauricio.best_travel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j //Para hacer logs
@AllArgsConstructor /*NOS PERMITE INYECTAR LAS DEPENDENCIAS DE LOS REPOSITORIOS O PODRIA HACER EL CONSTRUCTOR
MANUALMENTE*/
public class TicketService implements ITicketService {

    /*Inyectamos nuestros repositorios*/
    /*Loa repositorios son las interfaces que tienen los métodos SQL*/
    /*Necesitamos estos dos repositorios porque ticket necesita un vuelo y un customer*/
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;

    @Override
    public TicketResponse Create(TicketRequest request) {
        /*Para crear un ticket necesitamos un fly y un customer*/
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdCliente()).orElseThrow();
        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(charges_price_percentage)))
                .arrivalDate(BestTravelUtil.getArrivalHour())
                .departureDate(BestTravelUtil.getDepartureHour())
                .purchaseDate(LocalDate.now())
                .build();
        var ticketSaved = this.ticketRepository.save(ticketToPersist);

        this.customerHelper.increaseTourAndTicket(customer.getDni(), TicketService.class);

        /*Es como un printf donde colocas variables en la impresion*/
        log.info("Ticket saved with id: {}", ticketSaved.getId());
        return this.entityToResponse(ticketSaved);
    }

    @Override
    public TicketResponse Read(UUID id) {
        var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse Update(TicketRequest request, UUID id){
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow();
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charges_price_percentage)));
        ticketToUpdate.setArrivalDate(BestTravelUtil.getArrivalHour());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getDepartureHour());
        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated with id {}", ticketUpdated.getId());
        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void Delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal getFlyPriceWithTax(Long flyId) {
        var fly = this.flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(charges_price_percentage));
    }

    /*Metodo para mapear los datos de Etity a Respnse*/
    private TicketResponse entityToResponse (TicketEntity entity){
        var response = new TicketResponse();
        /*BeanUtils.copyProperties(); se encarga de mapear los atributos de TicketEntity con los de TicketResponse
        * por eso le pasamos la variable entity (datos de entidad) y responde (datos de un objeto response)*/
        BeanUtils.copyProperties(entity,response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(),flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    /*Creamos una constate para el impuesto de los vuelos*/
    public static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.25);

}
