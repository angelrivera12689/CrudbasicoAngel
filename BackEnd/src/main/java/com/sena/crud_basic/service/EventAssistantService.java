package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.EventAssistantDTO;
import com.sena.crud_basic.model.EventAssistant;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.IEventAssistant;

@Service
public class EventAssistantService {

    @Autowired
    private IEventAssistant data;

    public void save(EventAssistantDTO eventAssistantDTO) {
        EventAssistant eventAssistant = convertToModel(eventAssistantDTO);
        data.save(eventAssistant);
    }

    public EventAssistantDTO convertToDTO(EventAssistant eventAssistant) {
        return new EventAssistantDTO(
                eventAssistant.getId_event_assistant(),
                eventAssistant.getEvent(),
                eventAssistant.getAssistant()
        );
    }

    public EventAssistant convertToModel(EventAssistantDTO eventAssistantDTO) {
        Events event = eventAssistantDTO.getEvent();
        Assistant assistant = eventAssistantDTO.getAssistant();
        return new EventAssistant(event, assistant);
    }
}
