package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.request.ReservationRequest;
import com.PortaMauricio.best_travel.api.models.responses.ReservationResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "reservation")
@AllArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> post (@Valid @RequestBody ReservationRequest request){
        return ResponseEntity.ok(reservationService.Create(request));
    }

    @GetMapping (path = "{id}")
    public ResponseEntity<ReservationResponse> get (@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.Read(id));
    }

    @PutMapping (path = "{id}")
    public ResponseEntity<ReservationResponse> update (@Valid @RequestBody ReservationRequest request,
                                                       @PathVariable UUID id){
        return ResponseEntity.ok(reservationService.Update(request,id));
    }

    @DeleteMapping (path = "{id}")
    public ResponseEntity <Void> delete (@PathVariable UUID id){
        reservationService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getHotelTotalPrice (@RequestParam Long hotelId){
        return ResponseEntity.ok(Collections.singletonMap("hotelPrice", this.reservationService.getHotelTotalPrice(hotelId)));
    }
}