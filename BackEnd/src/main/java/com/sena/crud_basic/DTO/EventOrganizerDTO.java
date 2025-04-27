package com.sena.crud_basic.DTO;

import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.organizer;

public class EventOrganizerDTO {

    private int idEventOrganizer;
    private Events event;
    private organizer organizer;
    private boolean status;

    public EventOrganizerDTO(int idEventOrganizer, Events event, organizer organizer, boolean status) {
        this.idEventOrganizer = idEventOrganizer;
        this.event = event;
        this.organizer = organizer;
        this.status = status;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
