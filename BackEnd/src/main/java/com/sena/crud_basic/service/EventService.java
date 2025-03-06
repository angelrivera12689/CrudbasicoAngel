package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.repository.IEvent;  // Make sure this repository exists

@Service
public class EventService {

    @Autowired
    private IEvent data;  // Event Repository to interact with the database

    // Method to save an event from EventsDTO
    public void save(EventsDTO eventsDTO) {
        Events event = convertToModel(eventsDTO);  // Convert DTO to model
        data.save(event);  // Save event to the database
    }

    // Method to convert an Events entity to EventsDTO
    public EventsDTO convertToDTO(Events event) {
        return new EventsDTO(
                event.getEvent_name(),
                event.getDescription(),
                event.getDate(),
                event.getTime(),
                event.getLocation(),
                event.getCategory_id());
    }

    // Method to convert EventsDTO to an Events entity
    public Events convertToModel(EventsDTO eventsDTO) {
        return new Events(
                0,  // Assuming ID is auto-generated
                eventsDTO.getEventName(),
                eventsDTO.getDescription(),
                eventsDTO.getDate(),
                eventsDTO.getTime(),
                eventsDTO.getLocation(),
                eventsDTO.getCategoryId());
    }

    // Other business logic methods (if needed) can be added here
}
