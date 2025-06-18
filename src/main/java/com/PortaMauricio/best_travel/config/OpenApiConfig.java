package com.PortaMauricio.best_travel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Portafolio-RestAPI",
                version = "1.0",
                description = "Documentación de los Endpoints")
)
public class OpenApiConfig {
}
