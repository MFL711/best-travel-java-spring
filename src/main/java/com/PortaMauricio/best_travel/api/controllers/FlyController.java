package com.PortaMauricio.best_travel.api.controllers;

import com.PortaMauricio.best_travel.api.models.responses.FlyResponse;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IFlyService;
import com.PortaMauricio.best_travel.util.enums.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping (path = "fly")
@AllArgsConstructor
@Tag(name = "Fly")
public class FlyController {

    private final IFlyService flyService;

    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll (
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader (required = false)SortType sortType) {

        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.flyService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping (path = "less_price")
    public ResponseEntity<Set<FlyResponse>> readLessPrice(@RequestParam BigDecimal price) {
        var response = this.flyService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping (path = "price_Between")
    public ResponseEntity<Set<FlyResponse>> readBetweenPrice(
            @RequestParam BigDecimal min, @RequestParam BigDecimal max){

        var response = this.flyService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping (path = "origin_destiny")
    public ResponseEntity<Set<FlyResponse>> readByOriginDestiny(
            @RequestParam String origin, @RequestParam String destiny){

        var response = this.flyService.readByOriginDestiny(origin, destiny);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

}
