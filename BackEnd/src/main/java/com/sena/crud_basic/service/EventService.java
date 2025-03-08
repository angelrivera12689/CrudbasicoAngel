package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.repository.IEvent;

import java.util.Optional;

@Service
public class EventService {
    
    @Autowired
    private IEvent data;  // Event Repository to interact with the database
    
    // Method to save an event from EventDTO
    public void save(EventsDTO eventDTO) {
        Events event = convertToModel(eventDTO);  // Convert DTO to model
        data.save(event);  // Save event to the database
    }
    
    // Method to convert an Events entity to EventDTO
    public EventsDTO convertToDTO(Events events) {
        return new EventsDTO(
                events.getEventName(),
                events.getDescription(),
                events.getDate(),
                events.getTime(),
                events.getLocation(),
                events.getCategoryId());
    }
    
    // Method to convert EventDTO to an Events entity
    public Events convertToModel(EventsDTO eventDTO) {
        return new Events(
                0,  // Assuming ID is auto-generated
                eventDTO.getEventName(),
                eventDTO.getDescription(),
                eventDTO.getDate(),
                eventDTO.getTime(),
                eventDTO.getLocation(),
                eventDTO.getCategoryId());
    }
    
    // Other business logic methods (if needed) can be added here
}