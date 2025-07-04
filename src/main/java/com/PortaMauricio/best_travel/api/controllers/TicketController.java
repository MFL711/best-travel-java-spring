package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.request.TicketRequest;
import com.PortaMauricio.best_travel.api.models.responses.TicketResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
@Tag(name = "Ticket")
public class TicketController {

    /*Para inyectar dependencias usamos siempre las interfaces*/
    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post (@RequestBody TicketRequest request){
        return ResponseEntity.ok(ticketService.Create(request));
    }

    @GetMapping (path = "{id}")
    public ResponseEntity<TicketResponse> get (@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.Read(id));
    }
    @PutMapping (path = "{id}")
    public ResponseEntity<TicketResponse> update (@RequestBody TicketRequest request, @PathVariable UUID id){
        return ResponseEntity.ok(ticketService.Update(request,id));
    }

    @DeleteMapping (path = "{id}")
    public ResponseEntity <Void> delete (@PathVariable UUID id){
        ticketService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyTotalPrice (@RequestParam Long flyId){
        return ResponseEntity.ok(Collections.singletonMap("flyPrice", this.ticketService.getFlyPriceWithTax(flyId)));
    }



}