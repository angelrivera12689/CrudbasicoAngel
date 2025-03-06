package com.sena.crud_basic.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "Event") // Name of the table in the database
public class Events {

    // Attributes or columns of the entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")  // Name of the column in the database
    private int id_event;

    @Column(name = "event_name", length = 100, nullable = false)
    private String event_name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date")
    private String date; // You could use LocalDate instead of String for better date management

    @Column(name = "time")
    private LocalDateTime time; // You could use LocalTime if you only need to store the time

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "category_id")
    private int category_id;  // To relate the event to a category

    // Constructor
    public Events(int id_event, String event_name, String description, String date, LocalDateTime time, String location, int category_id) {
        this.id_event = id_event;
        this.event_name = event_name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.category_id = category_id;
    }

    // Getters and Setters

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
