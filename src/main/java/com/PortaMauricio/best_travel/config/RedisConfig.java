package com.PortaMauricio.best_travel.config;

import com.PortaMauricio.best_travel.util.constants.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Configuration // Indica que esta clase es una clase de configuración de Spring, se usa para definir Beans.
@EnableCaching // Habilita el soporte de caché en la aplicación, permite usar @Cacheable, @CacheEvict, etc.
@EnableScheduling // Habilita la programación de tareas en Spring, necesaria para usar @Scheduled.
@Slf4j // Anotación de Lombok que genera automáticamente un logger (log) en la clase.
public class RedisConfig {

    @Value(value = "${cache.redis.address}")
    private String serverAddress;
    // Inyecta el valor desde application.properties con la clave cache.redis.address.

    @Value(value = "${cache.redis.password}")
    private String serverPassword;
    // Inyecta el valor desde application.properties con la clave cache.redis.password.

    /**
     * Carga el cliente Redisson al contenedor de Spring.
     * RedissonClient es una implementación Java de Redis que ofrece soporte avanzado para estructuras de datos distribuidas.
     *
     * @return instancia configurada de RedissonClient
     */
    @Bean
    public RedissonClient redissonClient() {
        var config = new Config();
        config.useSingleServer() // Configura Redis en modo de un solo servidor.
                .setAddress(serverAddress) // Dirección del servidor Redis, inyectada desde el properties.
                .setPassword(serverPassword); // Contraseña de Redis, inyectada desde el properties.
        return Redisson.create(config); // Crea y devuelve el cliente Redisson listo para usarse.
    }

    /**
     * Configura el gestor de caché de Spring usando Redisson.
     * Permite que las anotaciones como @Cacheable funcionen usando Redis como almacenamiento.
     *
     * @param redissonClient Cliente de Redis proporcionado por el Bean anterior.
     * @return instancia de CacheManager basada en Redisson.
     */
    @Bean
    @Autowired // Inyecta automáticamente el Bean RedissonClient al método.
    public CacheManager cacheManager(RedissonClient redissonClient) {
        var configs = Map.of(
                CacheConstants.FLY_CACHE_NAME, new CacheConfig(),
                CacheConstants.HOTEL_CACHE_NAME, new CacheConfig()
        );
        // Crea un Map inmutable donde se definen los nombres de los cachés y su configuración.

        return new RedissonSpringCacheManager(redissonClient, configs);
        // Retorna el gestor de caché de Spring, configurado para usar Redis como backend.
    }

    /**
     * Limpia los datos de los cachés registrados periódicamente.
     * Este metodo se ejecuta automáticamente basado en una expresión cron.
     * Se eliminan todas las entradas de los cachés FLY_CACHE_NAME y HOTEL_CACHE_NAME.
     */
    @CacheEvict(
            cacheNames = {
                    CacheConstants.FLY_CACHE_NAME,
                    CacheConstants.HOTEL_CACHE_NAME
            },
            allEntries = true // Elimina todas las entradas de los cachés especificados.
    )
    @Scheduled(cron = CacheConstants.SCHEDULED_RESET_CACHE)
    // Programa la ejecución periódica de este metodo basado en una expresión cron, configurable en
    // constants.
    @Async
    // Ejecuta el metodo de forma asíncrona, en un hilo separado, sin bloquear la aplicación principal.
    public void deleteCache() {
        log.info("Clean Caché");
        // Muestra un mensaje en los logs indicando que el caché fue limpiado.
    }
}

