package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "event_employee")  // The name of the linking table for the many-to-many relationship
public class EventEmployee {

    /*
     * Attributes or columns of the entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_employee")  // Primary key of the table
    private int idEventEmployee;

    @ManyToOne
    @JoinColumn(name = "id_event")  // Foreign key to the Event entity
    private Events event;

    @ManyToOne
    @JoinColumn(name = "id_employee")  // Foreign key to the Employee entity
    private employee employee;

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    // Constructor
    public EventEmployee(Events event, employee employee, boolean status) {
        this.status=status;
        this.event = event;
        this.employee = employee;
    }

    // Getters and Setters
    public int getIdEventEmployee() {
        return idEventEmployee;
    }

    public void setIdEventEmployee(int idEventEmployee) {
        this.idEventEmployee = idEventEmployee;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public employee getEmployee() {
        return employee;
    }

    public void setEmployee(employee employee) {
        this.employee = employee;
    }
    public boolean getStatus() {
        return status;
     }
    
     public void setStatus(boolean status) {
        this.status = status;
     }
}
