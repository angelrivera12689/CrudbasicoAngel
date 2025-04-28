package com.sena.crud_basic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.model.EventAssistant;
import com.sena.crud_basic.model.Events;

public interface IEventAssistant extends JpaRepository<EventAssistant, Integer> {

    // Buscar por la relación entre evento y asistente
   Optional<EventAssistant> findByEventAndAssistant(Events event, Assistant assistant);

    // Buscar eventos por nombre de asistente
    @Query("SELECT ea FROM event_assistant ea JOIN ea.assistant a WHERE a.name LIKE %:name%")
    List<EventAssistant> findEventsByAssistantName(@Param("name") String name);

    // Buscar asistentes por nombre de evento
    @Query("SELECT ea FROM event_assistant ea JOIN ea.event e WHERE e.eventName LIKE %:name%")
    List<EventAssistant> findAssistantsByEventName(@Param("name") String name);
}
