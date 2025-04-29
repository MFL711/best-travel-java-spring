package com.PortaMauricio.best_travel.domain.entities;

import com.PortaMauricio.best_travel.util.AeroLine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name="fly")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data //Data implemeta los getters and setters
public class FlyEntity implements Serializable {

    //Mapeo de atributos de la entidad fly
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private BigDecimal price;
    private String destinyName;
    private String originName;
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;



}
