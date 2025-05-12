package com.PortaMauricio.best_travel.infraestructure.abstract_service;

import com.PortaMauricio.best_travel.api.models.request.TicketRequest;
import com.PortaMauricio.best_travel.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService <TicketRequest, TicketResponse, UUID>{

    BigDecimal getFlyPriceWithTax(Long flyId);
}
