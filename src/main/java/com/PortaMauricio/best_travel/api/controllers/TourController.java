package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.request.TourRequest;
import com.PortaMauricio.best_travel.api.models.responses.TourResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.ITourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
public class TourController {

    private final ITourService tourService;

    @PostMapping
    public ResponseEntity<TourResponse> post (@RequestBody TourRequest request){
        return ResponseEntity.ok(this.tourService.create(request));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> get (@PathVariable Long id){
        return ResponseEntity.ok(tourService.read(id));
    }

    @DeleteMapping (path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping (path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId,
                                                     @PathVariable UUID ticketId){
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping (path = "{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId,
                                                       @PathVariable Long flyId){
        var response = Collections.singletonMap("tickeId",this.tourService.addTicket(tourId, flyId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping (path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId,
                                             @PathVariable UUID reservationId){
        this.tourService.removeReservation(tourId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping (path = "{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addReservation(@PathVariable Long tourId,
                                                       @PathVariable Long hotelId,
                                                            @RequestParam Integer totalDays){
        //Solo cuando son id's se utiliza el path variable, cuando son datos que se le pide al cliente
        // de utiliza el Request Param
        var response = Collections.singletonMap("reservationId",this.tourService.addReservation(tourId,
                hotelId,
                totalDays));
        return ResponseEntity.ok(response);
    }
}
