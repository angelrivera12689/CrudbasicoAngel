package com.sena.crud_basic.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventsDTO {
    private String eventName;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private Integer categoryId;
    private String categoryName; // Asegúrate de tener este campo
    private String imageUrl;

    // Constructor con parámetros
    public EventsDTO(String eventName, String description, LocalDate date, LocalTime time,
                     String location, int categoryId, String imageUrl, String categoryName) {
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName; // El nombre de la categoría
    }

    // Getters y Setters
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
