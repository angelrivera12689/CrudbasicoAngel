package com.sena.crud_basic.model;

import jakarta.persistence.*;

@Entity(name = "Review") // Table name in the database
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private int idReview;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "event_id")  // Foreign key reference to Event
    private Events event;

    @ManyToOne
    @JoinColumn(name = "assistant_id")  // Foreign key reference to Assistant
    private Assistant assistant;

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    // Constructor vacío (necesario para JPA)
    public Review() {}

    // Constructor completo
    public Review(String comment, int rating, Events event, Assistant assistant, boolean status) {
        this.comment = comment;
        this.rating = rating;
        this.event = event;
        this.assistant = assistant;
        this.status=status;
    }

    // Getters and Setters
    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

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
    public boolean getStatus() {
        return status;
     }
    
     public void setStatus(boolean status) {
        this.status = status;
     }
}
