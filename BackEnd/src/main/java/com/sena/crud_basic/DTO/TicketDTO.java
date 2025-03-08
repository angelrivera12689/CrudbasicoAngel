package com.sena.crud_basic.DTO;

import java.time.LocalDateTime;

public class TicketDTO {
    private int idTicket;
    private int eventId;
    private int assistantId;
    private double price;
    private String seatNumber;
    private LocalDateTime purchaseDate;
    private String status;

    public TicketDTO(int idTicket, int eventId, int assistantId, double price, String seatNumber, LocalDateTime purchaseDate, String status) {
        this.idTicket = idTicket;
        this.eventId = eventId;
        this.assistantId = assistantId;
        this.price = price;
        this.seatNumber = seatNumber;
        this.purchaseDate = purchaseDate;
        this.status = status;
    }

    // Getters y Setters
    public int getIdTicket() {
        return idTicket;
    }

    public int getEventId() {
        return eventId;
    }

    public int getAssistantId() {
        return assistantId;
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

    public String getStatus() {
        return status;
    }
}
