package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.Events;

public interface IEvent extends JpaRepository<Events, Integer> {
    // You can add custom queries if needed
}
