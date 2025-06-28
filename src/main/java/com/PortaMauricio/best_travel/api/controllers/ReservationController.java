package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.request.ReservationRequest;
import com.PortaMauricio.best_travel.api.models.responses.ErrorsResponse;
import com.PortaMauricio.best_travel.api.models.responses.ReservationResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "reservation")
@AllArgsConstructor
@Tag(name = "Reservation") //En Swagger antes de esta notación el nombre que tenia el EndPont era
// reservation-controller, con esta notación modificamos a Reservation o cualquier otro nombre que
// nosotros coloquemos entre las comillas
public class ReservationController {

    private final IReservationService reservationService;

    @ApiResponse(
            responseCode = "400",
            description = "Cuando la petición tiene un campo invalido responderemos con este código",
            content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorsResponse.class))
            }
    )
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

    //Este metodo usará la consulta de conversion de moneda con el WebClient
    @Operation(summary = "Regresa el precio total (con impuestos) de un hotel dado el id del hotel ")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getHotelTotalPrice (
            // Recibe como parámetro en la URL el ID del hotel
            @RequestParam Long hotelId,
            // Recibe desde los headers la moneda deseada.
            @RequestHeader (required = false) Currency currency){
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        // Devuelve una respuesta HTTP 200 OK con un JSON que contiene solo la clave "hotelPrice"
        return ResponseEntity.ok(Collections.singletonMap("hotelPrice",
                this.reservationService.getHotelTotalPrice(hotelId, currency)));
    }
}