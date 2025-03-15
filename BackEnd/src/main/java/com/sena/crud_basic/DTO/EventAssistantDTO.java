package com.sena.crud_basic.DTO;

import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.model.Events;

public class EventAssistantDTO {

    private int idEventAssistant;
    private Events event;
    private Assistant assistant;

    
    // Constructor
    public EventAssistantDTO(int idEventAssistant, Events event, Assistant assistant) {
        this.idEventAssistant = idEventAssistant;
        this.event = event;
        this.assistant = assistant;
    }

    // Getters and Setters
    public int getIdEventAssistant() {
        return idEventAssistant;
    }

    public void setIdEventAssistant(int idEventAssistant) {
        this.idEventAssistant = idEventAssistant;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }
}
