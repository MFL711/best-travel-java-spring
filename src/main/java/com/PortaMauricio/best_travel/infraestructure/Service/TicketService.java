package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.request.TicketRequest;
import com.PortaMauricio.best_travel.api.models.responses.FlyResponse;
import com.PortaMauricio.best_travel.api.models.responses.TicketResponse;
import com.PortaMauricio.best_travel.domain.entities.TicketEntity;
import com.PortaMauricio.best_travel.domain.repositories.CustomerRepository;
import com.PortaMauricio.best_travel.domain.repositories.FlyRepository;
import com.PortaMauricio.best_travel.domain.repositories.TicketRepository;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public TicketResponse Create(TicketRequest request) {
        /*Para crear un ticket necesitamos un fly y un customer*/
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow();


        return null;
    }

    @Override
    public TicketResponse Read(UUID uuid) {
        return null;
    }

    @Override
    public TicketResponse Update(TicketRequest request, UUID uuid) {
        return null;
    }

    @Override
    public TicketResponse Delete(UUID uuid) {
        return null;
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
}
