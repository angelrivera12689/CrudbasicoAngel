package com.sena.crud_basic.Resource;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.github.bucket4j.Bandwidth;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final Bucket bucket;

    public RateLimiterService() {
        // 🔧 Capacidad máxima de tokens disponibles al mismo tiempo
        int capacity = 500;

        // 🕒 Recarga 500 tokens cada segundo (flujo constante)
        Refill refill = Refill.intervally(500, Duration.ofSeconds(1));

        // 📦 Configura el límite de ancho de banda
        Bandwidth limit = Bandwidth.classic(capacity, refill);

        // 🚰 Construye el bucket con la capacidad y el plan de recarga
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    // ✅ Intenta consumir 1 token por cada petición
    public boolean tryConsume() {
        return bucket.tryConsume(1);
    }
}

