package com.PortaMauricio.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data //Data implementa los getters and setters
public class ReservationEntity implements Serializable {

    //Para esta entidad nosotros vamos a generar el ID, no la base de datos
    @Id
    private UUID id;
    /*Como no tiene el mismo nombre el parametro tanto en la base de datos como en la variable que acabamos de
    * definir, usamos la notación colum para mapear correctamente el parametro*/
    @Column(name = "date_reservation")
    private LocalDateTime dateTimeReservation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer totalDays;
    private BigDecimal price;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn( name = "hotel_id")
    private HotelEntity hotel;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn( name = "tour_id", nullable = true )
    private TourEntity tour;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn( name = "customer_id")
    private CustomerEntity customer;


}
