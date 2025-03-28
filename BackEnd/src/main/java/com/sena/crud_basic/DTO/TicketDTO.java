package com.sena.crud_basic.DTO;

import java.time.LocalDateTime;

public class TicketDTO {
    private int idTicket;
    private int eventId;
    private int assistantId;
    private double price;
    private String seatNumber;
    private LocalDateTime purchaseDate;
    private boolean status;

    public TicketDTO(int idTicket, int eventId, int assistantId, double price, String seatNumber, LocalDateTime purchaseDate, boolean status) {
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

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
  
   

