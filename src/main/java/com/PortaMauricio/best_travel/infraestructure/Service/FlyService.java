package com.PortaMauricio.best_travel.infraestructure.Service;

import com.PortaMauricio.best_travel.api.models.responses.FlyResponse;
import com.PortaMauricio.best_travel.domain.entities.FlyEntity;
import com.PortaMauricio.best_travel.domain.repositories.FlyRepository;
import com.PortaMauricio.best_travel.infraestructure.abstract_service.IFlyService;
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

@Transactional (readOnly = true) //Solo operaciones de lectura en la base de datos
@Service
@AllArgsConstructor
@Data
@Slf4j
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return this.flyRepository.selectOriginDestiny(origin, destiny)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;
        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER ->pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return this.flyRepository.selectLessPrice(price)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.flyRepository.selectBetweenPrice(min, max)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toSet());
    }

    public FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;

    }
}
