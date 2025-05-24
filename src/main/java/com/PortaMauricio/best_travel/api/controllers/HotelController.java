package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.responses.HotelResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IHotelService;
import com.PortaMauricio.best_travel.util.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "hotel")
@AllArgsConstructor
public class HotelController {

    private final IHotelService hotelService;

    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll (
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader (required = false)SortType sortType){

        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.hotelService.readAll(page,size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);

    }

    @GetMapping (path = "less_price")
    public ResponseEntity<Set<HotelResponse>> readLessPrice (@RequestParam BigDecimal price){
        var response = this.hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping (path = "price_between")
    public ResponseEntity<Set<HotelResponse>> readBetweenPrice (
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max){

        var response = this.hotelService.readBetweenPrice(min,max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping (path = "rating")
    public ResponseEntity<Set<HotelResponse>> readByRating (@RequestParam Integer rating){

        var response = this.hotelService.readByRating(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


}
