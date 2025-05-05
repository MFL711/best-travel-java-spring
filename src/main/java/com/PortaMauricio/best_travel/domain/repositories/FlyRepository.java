package com.PortaMauricio.best_travel.domain.repositories;

import com.PortaMauricio.best_travel.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {

    //Aqui vamos a colocar querys personalizados
    //select [alias objeto] form [nombre de la entidad] [Alias de la entidad] where [alias objeto].propiedad condición
    /*el parametro en este caso price debe de tener el mismo nombre o de lo contrario especificarlo con la notación
    * @Param ("price"") justo antes de en este caso BigDecimal */
    @Query("select f from fly f where f.price < :price") //No poner espacio entre el parametro y los :
    Set<FlyEntity> selectLessPrice(BigDecimal price);

    @Query("select f from fly f where f.price between :min and :max" )
    Set<FlyEntity> selectBetweenPrice(BigDecimal min, BigDecimal max);

    @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny" )
    Set<FlyEntity> selectOriginDestiny(String origin, String destiny);

    /*Select f de la tabla fly alias f hacemos la union f.tickets alias t where t.id = parametro id*/
    //Dado un ticket nos da el vuelo correpondiente.
    @Query("select f from fly f join fetch f.tickets t where t.id = :id")
    Optional<FlyEntity> findByTicketId(UUID id);

}
