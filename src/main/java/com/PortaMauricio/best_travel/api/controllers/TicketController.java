package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.request.TicketRequest;
import com.PortaMauricio.best_travel.api.models.responses.TicketResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
public class TicketController {

    /*Para inyectar dependencias usamos siempre las interfaces*/
    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post (@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.Create(request));

    }

}