package com.sena.crud_basic.DTO;

public class ReviewDTO {
    private String comment;
    private int rating;
    private int eventId;
    private int assistantId;
    
    // Constructor vacío
    public ReviewDTO() {
    }
    
    // Constructor con parámetros
    public ReviewDTO(String comment, int rating, int eventId, int assistantId) {
        this.comment = comment;
        this.rating = rating;
        this.eventId = eventId;
        this.assistantId = assistantId;
    }
    
    // Getters y Setters
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public int getEventId() {
        return eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    public int getAssistantId() {
        return assistantId;
    }
    
    public void setAssistantId(int assistantId) {
        this.assistantId = assistantId;
    }
}