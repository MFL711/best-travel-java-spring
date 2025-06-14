package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.responses.HotelResponse;
import com.PortaMauricio.best_travel.domain.entities.HotelEntity;
import com.PortaMauricio.best_travel.domain.repositories.HotelRepository;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IHotelService;
import com.PortaMauricio.best_travel.util.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional (readOnly = true) //Solo permite lectura de la base de datos, no operaciones de insert o
// delete
@Service
@AllArgsConstructor
@Data
@Slf4j
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.hotelRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return this.hotelRepository.findByPriceLessThan(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.hotelRepository.findByPriceBetween(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }
    
    @Override
    public Set<HotelResponse> readByRating(Integer rating) {

        return this.hotelRepository.findByRatingGreaterThan(rating)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    public HotelResponse entityToResponse (HotelEntity entity){
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
}
