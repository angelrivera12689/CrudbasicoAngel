package com.sena.crud_basic.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_basic.Resource.RateLimiterService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final RateLimiterService rateLimiter;

    public TestController(RateLimiterService rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @GetMapping
    public ResponseEntity<String> testRateLimit() {
        if (!rateLimiter.tryConsume()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("🚫 Límite de peticiones alcanzado");
        }
        return ResponseEntity.ok("✅ ¡Petición permitida!");
    }
}


