package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.EventOrganizerDTO;
import com.sena.crud_basic.model.EventOrganizer;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.organizer;
import com.sena.crud_basic.repository.IEventOrganizer;

@Service
public class EventOrganizerService {

    @Autowired
    private IEventOrganizer data;

    public void save(EventOrganizerDTO eventOrganizerDTO) {
        EventOrganizer eventOrganizer = convertToModel(eventOrganizerDTO);
        data.save(eventOrganizer);
    }

    public EventOrganizerDTO convertToDTO(EventOrganizer eventOrganizer) {
        return new EventOrganizerDTO(
                eventOrganizer.getId_event_organizer(),
                eventOrganizer.getEvent(),
                eventOrganizer.getOrganizer()
        );
    }

    public EventOrganizer convertToModel(EventOrganizerDTO eventOrganizerDTO) {
        Events event = eventOrganizerDTO.getEvent();
        organizer organizer = eventOrganizerDTO.getOrganizer();
        return new EventOrganizer(event, organizer, true);
    }
}
