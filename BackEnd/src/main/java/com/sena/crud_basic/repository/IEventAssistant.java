package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.Assistant;

public interface IEventAssistant extends JpaRepository<Assistant, Integer> {
    // You can add custom queries if needed
}
