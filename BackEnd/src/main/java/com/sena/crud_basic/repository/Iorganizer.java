package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.organizer;

public interface Iorganizer extends JpaRepository<organizer, Integer> {
    // You can add custom queries if needed
}
