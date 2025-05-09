package com.PortaMauricio.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity (name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketEntity implements Serializable {

    @Id
    private UUID id; //Nosotros lo generamos
    private BigDecimal price;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private LocalDateTime purchaseDate;
    //Muchos tickets pertenecen a un vuelo
    @ManyToOne
    @JoinColumn (name = "fly_id") /*Es el nombre que tiene el atributo
    en la tabla de ticket*/
    //Especifica la columna de unión entre las dos tablas.
    private FlyEntity fly; //

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private TourEntity tour;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
