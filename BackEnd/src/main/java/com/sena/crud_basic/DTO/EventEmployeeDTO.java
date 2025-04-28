package com.sena.crud_basic.DTO;

import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.employee;  // Correcto según las convenciones Java

public class EventEmployeeDTO {

    // Primary key of the EventEmployee relationship (if needed)
    private int idEventEmployee;

    // Foreign key to the Event entity
    private Events event;

    // Foreign key to the Employee entity
    private employee employee;  // Cambiado de "employee" a "Employee"

    // Constructor
    public EventEmployeeDTO(int idEventEmployee, Events event, employee employee) {
        this.idEventEmployee = idEventEmployee;
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
}
