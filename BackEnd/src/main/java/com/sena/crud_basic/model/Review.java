package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "Review") // Table name in the database
public class Review {

    // Attributes or columns of the entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")  // Column name in the database
    private int id_review;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "rating")  // Rating is from 1 to 5
    private int rating;

    // Foreign keys
    @ManyToOne
    @Column(name = "event_id")
    private int event_id;  // Foreign key reference to Evento (Event)

    @ManyToOne
    @Column(name = "assistant_id")
    private int assistant_id;  // Foreign key reference to Asistente (Assistant)

    // Constructor
    public Review(int id_review, String comment, int rating, int event_id, int assistant_id) {
        this.id_review = id_review;
        this.comment = comment;
        this.rating = rating;
        this.event_id = event_id;
        this.assistant_id = assistant_id;
    }

    // Getters and Setters
    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
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

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getAssistant_id() {
        return assistant_id;
    }

    public void setAssistant_id(int assistant_id) {
        this.assistant_id = assistant_id;
    }
}
