package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.Review;

public interface IReview extends JpaRepository<Review, Integer> {
    // You can add custom queries if needed
}
