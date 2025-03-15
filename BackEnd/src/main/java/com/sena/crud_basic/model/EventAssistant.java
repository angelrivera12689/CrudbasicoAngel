package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "event_assistant")  // The name of the linking table for the many-to-many relationship
public class EventAssistant {

    /*
     * Attributes or columns of the entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_assistant")  // Primary key of the table
    private int id_event_assistant;

    @ManyToOne
    @JoinColumn(name = "id_event")  // Foreign key to the Event entity
    private Events event;

    @ManyToOne
    @JoinColumn(name = "id_assistant")  // Foreign key to the Assistant entity
    private Assistant assistant;
    
    public EventAssistant() {
    }
    // Constructor
    public EventAssistant(Events event, Assistant assistant) {
        this.event = event;
        this.assistant = assistant;
    }

    // Getters and Setters
    public int getId_event_assistant() {
        return id_event_assistant;
    }

    public void setId_event_assistant(int id_event_assistant) {
        this.id_event_assistant = id_event_assistant;
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
