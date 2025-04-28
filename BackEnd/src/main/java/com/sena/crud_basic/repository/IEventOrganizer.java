package com.sena.crud_basic.repository;

import java.util.List;
import java.util.Optional;

import com.sena.crud_basic.model.EventOrganizer;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.organizer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEventOrganizer extends JpaRepository<EventOrganizer, Integer> {

    // Query to find organizers by event name
    @Query("SELECT eo FROM event_organizer eo WHERE eo.event.eventName = :name")
    List<EventOrganizer> findOrganizersByEventName(@Param("name") String name);

    // Corrected query to find events by organizer's name
    @Query("SELECT eo FROM event_organizer eo WHERE eo.organizer.name = :name")
    List<EventOrganizer> findEventsByOrganizerName(@Param("name") String name);

    Optional<EventOrganizer> findByEventAndOrganizer(Events event, organizer organizer);
}
