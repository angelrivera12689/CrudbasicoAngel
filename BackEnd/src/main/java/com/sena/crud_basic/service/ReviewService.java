package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.ReviewDTO;
import com.sena.crud_basic.model.Review;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.IReview;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IAssistant;

import java.util.Optional;

@Service
public class ReviewService {
    
    @Autowired
    private IReview reviewRepository;
    
    @Autowired
    private IEvent eventRepository;
    
    @Autowired
    private IAssistant assistantRepository;
    
    // Method to save a review from ReviewDTO
    public void save(ReviewDTO reviewDTO) {
        Review review = convertToModel(reviewDTO);  // Convert DTO to model
        reviewRepository.save(review);  // Save review to the database
    }
    
    // Method to convert a Review entity to ReviewDTO
    public ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getComment(),
                review.getRating(),
                review.getEvent().getIdEvent(),
                review.getAssistant().getId()  // Assuming Assistant has getId() method
        );
    }
    
    // Method to convert ReviewDTO to a Review entity
    public Review convertToModel(ReviewDTO reviewDTO) {
        // Get the Event and Assistant entities from their repositories
        Events event = eventRepository.findById(reviewDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + reviewDTO.getEventId()));
        
        Assistant assistant = assistantRepository.findById(reviewDTO.getAssistantId())
                .orElseThrow(() -> new RuntimeException("Assistant not found with id: " + reviewDTO.getAssistantId()));
        
        // Create and return the Review entity
        return new Review(
                reviewDTO.getComment(),
                reviewDTO.getRating(),
                event,
                assistant
        );
    }
}