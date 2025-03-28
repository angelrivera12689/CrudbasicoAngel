package com.sena.crud_basic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

@Entity
@Table(name = "Event") // Nombre de la tabla en la base de datos
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private int idEvent;

    @Column(name = "event_name", length = 100, nullable = false)
    private String eventName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;  // LocalDate para fechas

    @Column(name = "time", nullable = false)
    private LocalTime time;  // LocalTime para horas

    @Column(name = "location", length = 150)
    private String location;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // Se alinea con la base de datos
    private CategoryEvent categoryEvent;
    

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    // Constructor vacío para JPA
    public Events() {
    }

    // Constructor con parámetros
    public Events(CategoryEvent categoryEvent, int idEvent, String eventName, String description,
                  LocalDate date, LocalTime time, String location, boolean status) {
        this.idEvent = idEvent;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.categoryEvent = categoryEvent;
        this.status = status;
    }

    // Getters y Setters
    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CategoryEvent getCategoryEvent() {
        return categoryEvent;
    }

    public void setCategoryEvent(CategoryEvent categoryEvent) {
        this.categoryEvent = categoryEvent;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
