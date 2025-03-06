package com.sena.crud_basic.DTO;

public class ReviewDTO {

    private String comment;
    private int rating;
    private int eventId;
    private int userId;

    // Constructor
    public ReviewDTO(String comment, int rating, int eventId, int userId) {
        this.comment = comment;
        this.rating = rating;
        this.eventId = eventId;
        this.userId = userId;
    }

    // Getters and Setters
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
