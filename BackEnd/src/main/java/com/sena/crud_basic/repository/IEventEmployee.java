package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sena.crud_basic.model.EventEmployee;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.employee;

import java.util.List;

public interface IEventEmployee extends JpaRepository<EventEmployee, Integer> {

    // Buscar empleados por nombre del evento
    @Query("SELECT ee FROM event_employee ee JOIN ee.event e WHERE e.eventName LIKE %:eventName%")
    List<EventEmployee> findEmployeesByEventName(@Param("eventName") String eventName);

    // Buscar eventos por nombre del empleado
    @Query("SELECT ee FROM event_employee ee JOIN ee.employee e WHERE e.first_name LIKE %:employeeName%")
    List<EventEmployee> findEventsByEmployeeName(@Param("employeeName") String employeeName);

    // Verificar si ya existe una relación entre el empleado y el evento
    @Query("SELECT ee FROM event_employee ee WHERE ee.event = :event AND ee.employee = :employee")
    EventEmployee findByEventAndEmployee(@Param("event") Events event, @Param("employee") employee employee);
}
