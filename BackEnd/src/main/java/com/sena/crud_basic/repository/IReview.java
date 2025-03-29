package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.Review;

public interface IReview extends JpaRepository<Review, Integer> {
 @Query("SELECT r FROM Review r WHERE r.status != false")
    List<Review> getListReviewActive();


      @Query("SELECT r FROM Review r WHERE " +
           "(:comment IS NULL OR r.comment LIKE %:comment%) AND " +
           "(:rating IS NULL OR r.rating = :rating) AND " +
           "(:eventId IS NULL OR r.event.id = :eventId) AND " +
           "(:assistantId IS NULL OR r.assistant.id = :assistantId) AND " +
           "(:status IS NULL OR r.status = :status)")
    List<Review> filterReviews(
        @Param("comment") String comment,
        @Param("rating") Integer rating,
        @Param("eventId") Integer eventId,
        @Param("assistantId") Integer assistantId,
        @Param("status") Boolean status
    );
}

