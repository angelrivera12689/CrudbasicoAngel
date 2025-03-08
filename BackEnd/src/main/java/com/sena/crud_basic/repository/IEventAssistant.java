package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.model.EventAssistant;

public interface IEventAssistant extends JpaRepository<Assistant, Integer> {

    void save(EventAssistant eventAssistant);
    // You can add custom queries if needed
}
