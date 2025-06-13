package com.PortaMauricio.best_travel.api.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest implements Serializable {

    @Size(min = 18, max = 20, message = "The size have to a length between  18 and 30 characteres")
    @NotBlank(message = "Id client is mandatory")
    private String customerId;
    @Positive
    @NotNull (message = "Id hotel is mandatory")
    private Long hotelId;
    @Min(value = 1, message = "Min 1 day to make reservation")
    @Max(value = 30, message = "Max 30 days to make reservation")
    @NotNull (message = "Total days is mandatory")
    private Integer totalDays;
    //@Pattern(regexp = "^(.+)@(.+)$") Aqui se utiliza una expresion o un patron de como debe de estar
    // escrito nuesto email, sin embargo, jakarta ya tiene su propia notation que nos permite tener una
    // validación más precisa.
    @Email
    private String email;

    //Agregando validaciones
}
