package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.ReviewDTO;
import com.sena.crud_basic.model.Review;
import com.sena.crud_basic.service.ReviewService;
import com.sena.crud_basic.Resource.RateLimiterService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RateLimiterService rateLimiter;

    // ✅ Método para verificar límite
    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Crear una nueva reseña
    @PostMapping("/")
    public ResponseEntity<Object> createReview(@RequestBody ReviewDTO reviewDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = reviewService.save(reviewDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Obtener todas las reseñas
    @GetMapping("/")
    public ResponseEntity<Object> getAllReviews() {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // ✅ Obtener reseñas por eventId
    @GetMapping("/event/{eventId}")
    public ResponseEntity<Object> getReviewsByEventId(@PathVariable int eventId) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        List<Review> reviews = reviewService.findByEventId(eventId);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO("404", "No se encontraron reseñas para este evento"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // ✅ Obtener una reseña por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReviewById(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        Optional<Review> review = reviewService.findById(id);
        if (!review.isPresent()) {
            return new ResponseEntity<>("Reseña no encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    // ✅ Eliminar una reseña por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = reviewService.deleteReview(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Filtrar reseñas
    @GetMapping("/filter")
    public ResponseEntity<Object> filterReviews(
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer eventId,
            @RequestParam(required = false) Integer assistantId,
            @RequestParam(required = false) Boolean status) {

        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        List<Review> reviews = reviewService.filterReviews(comment, rating, eventId, assistantId, status);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // ✅ Actualizar reseña
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable int id, @RequestBody ReviewDTO reviewDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = reviewService.update(id, reviewDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}