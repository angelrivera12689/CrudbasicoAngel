package com.sena.crud_basic.DTO;

import java.time.LocalDateTime;

public class EventsDTO {

    private String eventName;
    private String description;
    private String date;  // You could consider using LocalDate instead of String for better date handling
    private LocalDateTime time;  // LocalDateTime is ideal if you need both date and time
    private String location;
    private int categoryId;  // Added categoryId field for the category relationship

    // Constructor
    public EventsDTO(String eventName, String description, String date, LocalDateTime time, String location, int categoryId) {
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.categoryId = categoryId;  // Initialize categoryId
    }

    // Getters and Setters
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
