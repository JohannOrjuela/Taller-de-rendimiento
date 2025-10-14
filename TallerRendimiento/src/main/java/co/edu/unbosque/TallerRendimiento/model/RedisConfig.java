package co.edu.unbosque.TallerRendimiento.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            // Deshabilita el caching de valores NULL (buena práctica)
            .disableCachingNullValues()
            // Configura el TTL por defecto para todos los cachés (aún así, el properties lo sobrescribe)
            .entryTtl(Duration.ofMinutes(10)) 
            // Usa el serializador Jackson para almacenar valores como JSON
            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}