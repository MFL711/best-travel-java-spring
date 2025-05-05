package com.PortaMauricio.best_travel.domain.repositories;

import com.PortaMauricio.best_travel.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface HotelRepository extends JpaRepository <HotelEntity, Long> {

    Set<HotelEntity> findByPriceLessThan(BigDecimal price);

    Set<HotelEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

    Set<HotelEntity> findByRatingGreaterThan(Integer rating);

    HotelEntity findByReservationId(UUID id);
}
