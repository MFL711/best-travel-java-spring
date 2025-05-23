package com.PortaMauricio.best_travel.domain.repositories;

import com.PortaMauricio.best_travel.domain.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
