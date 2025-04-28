package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "event_employee")  // Nombre de la tabla de la relación muchos a muchos
public class EventEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_employee")  // Clave primaria de la tabla
    private int idEventEmployee;

    @ManyToOne
    @JoinColumn(name = "id_event")  // Clave foránea para la entidad Event
    private Events event;

    @ManyToOne
    @JoinColumn(name = "id_employee")  // Clave foránea para la entidad employee (con minúscula)
    private employee employee;  // Nombre correcto de la clase es 'employee' (minúscula)

    @Column(name="status",nullable = false, columnDefinition = "boolean default true")
    private boolean status;
  // 1) Constructor vacío necesario para JPA
  public EventEmployee() {
}
    // Constructor
    public EventEmployee(Events event, employee employee, boolean status) {
        this.event = event;
        this.employee = employee;
        this.status = status;
    }

    // Getters y Setters
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
