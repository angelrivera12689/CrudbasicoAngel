package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_basic.model.Review;

public interface IReview extends JpaRepository<Review, Integer> {
 @Query("SELECT r FROM Review r WHERE r.status != false")
    List<Review> getListReviewActive();
}
