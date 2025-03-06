package com.sena.crud_basic.DTO;

import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.organizer;  // Corrected class name to "Organizer" as per Java conventions

public class EventOrganizerDTO {

    // Primary key of the EventOrganizer relationship
    private int idEventOrganizer;

    // Foreign key to the Event entity
    private Events event;

    // Foreign key to the Organizer entity
    private organizer organizer;

    // Constructor
    public EventOrganizerDTO(int idEventOrganizer, Events event, organizer organizer) {
        this.idEventOrganizer = idEventOrganizer;
        this.event = event;
        this.organizer = organizer;
    }

    // Getters and Setters
    public int getIdEventOrganizer() {
        return idEventOrganizer;
    }

    public void setIdEventOrganizer(int idEventOrganizer) {
        this.idEventOrganizer = idEventOrganizer;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(organizer organizer) {
        this.organizer = organizer;
    }
}
