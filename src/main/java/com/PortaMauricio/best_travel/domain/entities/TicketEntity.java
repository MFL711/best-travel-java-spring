package com.PortaMauricio.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity (name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketEntity implements Serializable {

    @Id
    private UUID id;
    private BigDecimal price;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalDate purchaseDate;
    //Muchos tickets pertenecen a un vuelo
    @ManyToOne
    @JoinColumn (name = "fly_id") /*Es el nombre que tiene el atributo
    en la tabla de ticket*/
    //Especifica la columna de unión entre las dos tablas.
    private FlyEntity fly;

}
