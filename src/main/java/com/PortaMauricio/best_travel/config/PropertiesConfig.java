package com.PortaMauricio.best_travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.Serializable;
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:configs/api_currency.properties"),
        @PropertySource(value = "classpath:configs/redis.properties")
})
public class PropertiesConfig implements Serializable {
}
