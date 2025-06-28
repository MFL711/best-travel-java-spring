package com.PortaMauricio.best_travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;
@Configuration
@PropertySource(value = "classpath:configs/api_currency.properties")
public class PropertiesConfig implements Serializable {
}
