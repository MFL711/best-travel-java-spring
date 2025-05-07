package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.request.TicketRequest;
import com.PortaMauricio.best_travel.api.models.responses.TicketResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITicketService;

import java.util.UUID;

public class TicketService implements ITicketService {
    @Override
    public TicketResponse Create(TicketRequest request) {
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
}
