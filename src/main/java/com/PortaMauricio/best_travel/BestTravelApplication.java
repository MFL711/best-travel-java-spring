package com.PortaMauricio.best_travel;

import com.PortaMauricio.best_travel.domain.entities.ReservationEntity;
import com.PortaMauricio.best_travel.domain.entities.TicketEntity;
import com.PortaMauricio.best_travel.domain.entities.TourEntity;
import com.PortaMauricio.best_travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

	private final HotelRepository hotelRepository;
	private final FlyRepository flyRepository;
	private final CustomerRepository customerRepository;
	private final TicketRepository ticketRepository;
	private final TourRepository tourRepository;
	private final ReservationRepository reservationRepository;

    public BestTravelApplication(HotelRepository hotelRepository, FlyRepository flyRepository,
								 CustomerRepository customerRepository, TicketRepository ticketRepository,
								 TourRepository tourRepository, ReservationRepository reservationRepository)
	{
        this.hotelRepository = hotelRepository;
        this.flyRepository = flyRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.tourRepository = tourRepository;
        this.reservationRepository = reservationRepository;
    }


    public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);



	}

	@Override
	public void run(String... args) throws Exception {
//		var fly = flyRepository.findById(15L).get();
//		var hotel = hotelRepository.findById(7L).get();
//		var ticket = ticketRepository.findById(UUID.fromString("42345678-1234-5678-5233-567812345678")).get();
//		var customer = customerRepository.findById("VIKI771012HMCRG093").get();
//
//		log.info(String.valueOf(fly));
//		log.info(String.valueOf(hotel));
//		log.info(String.valueOf(ticket));

		/*Probando los querys de JPQL*/

//		this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(System.out::println);
//		this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(50), BigDecimal.valueOf(100)).forEach(System.out::println);
//		this.flyRepository.selectOriginDestiny("Mexico","Canada").forEach(System.out::println);

		/*Probando join de ticket*/
		//Buscando el vuelo pasasndo como parametro el id del ticket

//		var fly = flyRepository.findByTicketId(UUID.fromString("22345678-1234-5678-3235-567812345678")).get();
//		System.out.println(fly);

		/*Probando consultas con palabras clave de JPA*/
//		hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);
//
//		hotelRepository.findByPriceBetween(BigDecimal.valueOf(50), BigDecimal.valueOf(100)).forEach(System.out::println);
//
//		hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);
//
//		var hotel = hotelRepository.findByReservationId(UUID.fromString("32345678-1234-5678-1234-567812345678"));
//		System.out.println("hotel = " + hotel);

		/*Esto sirvio para hacer pruebas al termiar de colocar todas las entidades, las consultas SQL, las diferentes
		* relaciones que existian entre los datos. Se creo un customer, un fly y un hotel para poder crear un tour y
		* después se eliminó*/

//		var customer = customerRepository.findById("BBMB771012HMCRR022").orElseThrow();
//		var fly = flyRepository.findById(11L).orElseThrow();
//		var hotel = hotelRepository.findById(3L).orElseThrow();
//
//		log.info("Nombre del cliente: " + customer.getFullName());
//		log.info("Hotle: " + hotel.getName());
//		log.info("fly: " + fly.getOriginName() +" - " + fly.getDestinyName());
//
//		var tour = TourEntity.builder() //Crea un objeto de la clase tour entity
//				.customer(customer)
//				.build();
//
//		var ticket = TicketEntity.builder()
//				.id(UUID.randomUUID())
//				.price(fly.getPrice().multiply(BigDecimal.TEN))
//				.arrivalDate(LocalDateTime.now())
//				.departureDate(LocalDateTime.now())
//				.purchaseDate(LocalDateTime.now())
//				.customer(customer)
//				.tour(tour)
//				.fly(fly)
//				.build();
//
//		var reservation = ReservationEntity.builder()
//				.id(UUID.randomUUID())
//				.dateTimeReservation(LocalDateTime.now())
//				.dateEnd(LocalDate.now().plusDays(3))
//				.dateStart(LocalDate.now().plusDays(1))
//				.hotel(hotel)
//				.customer(customer)
//				.tour(tour)
//				.totalDays(3)
//				.price(hotel.getPrice().multiply(BigDecimal.TEN))
//				.build();
//
//		System.out.println("Guardando tour\n");
//
//		tour.addReservation(reservation);
//		tour.updateReservations();
//		tour.addTicket(ticket);
//		tour.updateTicket();
//
//		var tourSaved = this.tourRepository.save(tour);
//
//		this.tourRepository.deleteById(tourSaved.getId());



	}
}
