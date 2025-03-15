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

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    
    // Crear una nueva reseña
    @PostMapping("/")
    public ResponseEntity<Object> createReview(@RequestBody ReviewDTO reviewDTO) {
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
        List<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // ✅ Obtener una reseña por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReviewById(@PathVariable int id) {
        Optional<Review> review = reviewService.findById(id);
        if (!review.isPresent()) {
            return new ResponseEntity<>("Reseña no encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    // ✅ Eliminar una reseña por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable int id) {
        ResponseDTO response = reviewService.deleteReview(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}