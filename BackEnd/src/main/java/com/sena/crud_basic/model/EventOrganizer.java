package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "event_organizer")  // The name of the linking table for the many-to-many relationship
public class EventOrganizer {

    /*
     * Attributes or columns of the entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_organizer")  // Primary key of the table
    private int id_event_organizer;

    @ManyToOne
    @JoinColumn(name = "id_event")  // Foreign key to the Event entity
    private Events event;

    @ManyToOne
    @JoinColumn(name = "id_organizer")  // Foreign key to the Organizer entity
    private organizer organizer;

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    // Constructor
    public EventOrganizer(Events event, organizer organizer, boolean status) {
        this.event = event;
        this.organizer = organizer;
        this.status=status;
    }

    // Getters and Setters
    public int getId_event_organizer() {
        return id_event_organizer;
    }

    public void setId_event_organizer(int id_event_organizer) {
        this.id_event_organizer = id_event_organizer;
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
    public boolean getStatus() {
        return status;
     }
    
     public void setStatus(boolean status) {
        this.status = status;
     }
}

