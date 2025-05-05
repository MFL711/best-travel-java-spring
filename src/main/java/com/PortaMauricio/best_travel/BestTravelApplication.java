package com.PortaMauricio.best_travel;

import com.PortaMauricio.best_travel.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
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

		var fly = flyRepository.findByTicketId(UUID.fromString("22345678-1234-5678-3235-567812345678")).get();
		System.out.println(fly);
	}
}
