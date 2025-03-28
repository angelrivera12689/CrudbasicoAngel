package com.sena.crud_basic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTicket;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Events event;

    @ManyToOne
    @JoinColumn(name = "assistant_id", nullable = false)
    private Assistant assistant;

    private double price;
    private String seatNumber;
    private LocalDateTime purchaseDate;
    private boolean status;

    // Constructores
    public Ticket() {}

    public Ticket(Events event, Assistant assistant, double price, String seatNumber, LocalDateTime purchaseDate, boolean status) {
        this.event = event;
        this.assistant = assistant;
        this.price = price;
        this.seatNumber = seatNumber;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }

    // Getters y Setters
    public int getIdTicket() {
        return idTicket;
    }

    public Events getEvent() {
        return event;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public double getPrice() {
        return price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
