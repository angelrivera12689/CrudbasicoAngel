package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.ReviewDTO;
import com.sena.crud_basic.model.Review;

import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.IReview;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IAssistant;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    
    @Autowired
    private IReview reviewRepository;
    
    @Autowired
    private IEvent eventRepository;
    
    @Autowired
    private IAssistant assistantRepository;
    
     public ResponseDTO save(ReviewDTO reviewDTO) {
        // Validar que el comentario no sea nulo o vacío
        if (reviewDTO.getComment() == null || reviewDTO.getComment().trim().isEmpty()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El comentario no puede estar vacío"
            );
        }

        // Validar que la calificación esté entre 1 y 5
        if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La calificación debe estar entre 1 y 5"
            );
        }

        // Validar que el evento exista
        Optional<Events> eventOpt = eventRepository.findById(reviewDTO.getEventId());
        if (!eventOpt.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El evento especificado no existe"
            );
        }

        // Validar que el asistente exista
        Optional<Assistant> assistantOpt = assistantRepository.findById(reviewDTO.getAssistantId());
        if (!assistantOpt.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El asistente especificado no existe"
            );
        }

        // Convertir DTO a modelo y guardar
        Review review = convertToModel(reviewDTO);
        reviewRepository.save(review);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Reseña guardada exitosamente"
        );
    }

    // ✅ Método para obtener todas las reseñas
    public List<Review> findAll() {
        return reviewRepository.getListReviewActive();
    }

    // ✅ Método para buscar una reseña por ID
    public Optional<Review> findById(int id) {
        return reviewRepository.findById(id);
    }

    // ✅ Método para eliminar una reseña por ID
    public ResponseDTO deleteReview(int id) {
        // Buscar la reseña por ID
        Optional<Review> review = findById(id);
        if (!review.isPresent()) {
            // Si no se encuentra la reseña, devolvemos una respuesta de error
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El registro no existe"
            );
        }
    
        // Cambiar el estado de la reseña a false (eliminación lógica)
        review.get().setStatus(false);
        reviewRepository.save(review.get()); // Usamos el repositorio correcto
    
        // Devolvemos una respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Reseña eliminada correctamente"
        );
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
                assistant, true
        );
    }
}