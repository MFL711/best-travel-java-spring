package com.PortaMauricio.best_travel.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tour")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "dni")
//    private CustomerEntity customerId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<TicketEntity> tickets;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<ReservationEntity> reservations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    public void addTicket(TicketEntity ticket){
        if (Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.add(ticket);
    }

    /*Va a recorrer la lista de tickets y usando la función remove if, va a checar si está el ticet que deseamos
    * eliminar y lo eliminará*/
    public void removeTicket(UUID id){
        if (Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.removeIf (ticket -> ticket.getId().equals(id));
    }

    @PreUpdate
    public void updateTicket(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

    /*Utilizamos la funcion isNull de la clase Objetct para determinar si har reservaiones, en caso de que no,
    * inicializa la colección y posteriromente agrega la reservacion, si ya contiene algo solo agrega. */
    public void addReservation(ReservationEntity reservation){
        if (Objects.isNull(this.reservations))
            this.reservations = new HashSet<>();
        this.reservations.add(reservation);
    }

    public void removeReservation(UUID idReservation){
        if (Objects.isNull(this.reservations)) this.reservations = new HashSet<>();
        this.reservations.removeIf(reservation -> reservation.getId().equals(idReservation));
    }

    public void updateReservations (){
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }


}
