package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.EventEmployeeDTO;
import com.sena.crud_basic.model.EventEmployee;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.employee;
import com.sena.crud_basic.repository.IEventEmployee;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.Iemployee;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventEmployeeService {

    @Autowired
    private IEventEmployee data;

    @Autowired
    private IEvent eventRepository;

    @Autowired
    private Iemployee employeeRepository;

    // Método para guardar la relación entre evento y empleado
    public void save(EventEmployeeDTO eventEmployeeDTO) {
        // Validar si el evento existe y está activo
        Events event = eventRepository.findById(eventEmployeeDTO.getEvent().getEventId())
                .orElseThrow(() -> new IllegalArgumentException("El evento no existe."));
        if (!event.getStatus()) {
            throw new IllegalArgumentException("El evento no está activo.");
        }

        // Validar si el empleado existe y está activo
        employee employee = employeeRepository.findById(eventEmployeeDTO.getEmployee().getId_employee())
                .orElseThrow(() -> new IllegalArgumentException("El empleado no existe."));
        if (!employee.getStatus()) {
            throw new IllegalArgumentException("El empleado no está activo.");
        }

        // Validar si el empleado ya está asignado al evento
        if (isEmployeeAlreadyAssignedToEvent(event, employee)) {
            throw new IllegalArgumentException("Este empleado ya está asignado al evento.");
        }

        EventEmployee eventEmployee = convertToModel(eventEmployeeDTO);
        data.save(eventEmployee);
    }

    // Método para verificar si un empleado ya está asignado a un evento
    private boolean isEmployeeAlreadyAssignedToEvent(Events event, employee employee) {
        EventEmployee existingRelation = data.findByEventAndEmployee(event, employee);
        return existingRelation != null;
    }

    // Método para convertir de EventEmployee a EventEmployeeDTO
    public EventEmployeeDTO convertToDTO(EventEmployee eventEmployee) {
        return new EventEmployeeDTO(
                eventEmployee.getIdEventEmployee(),
                eventEmployee.getEvent(),
                eventEmployee.getEmployee()
        );
    }

    // Método para convertir de EventEmployeeDTO a EventEmployee
    public EventEmployee convertToModel(EventEmployeeDTO eventEmployeeDTO) {
        Events event = eventEmployeeDTO.getEvent();
        employee employee = eventEmployeeDTO.getEmployee();
        return new EventEmployee(event, employee, true);
    }

    // Método para obtener empleados por nombre del evento
    public List<EventEmployeeDTO> getEmployeesByEventName(String eventName) {
        List<EventEmployee> eventEmployees = data.findEmployeesByEventName(eventName);
        return eventEmployees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener eventos por nombre del empleado
    public List<EventEmployeeDTO> getEventsByEmployeeName(String employeeName) {
        List<EventEmployee> eventEmployees = data.findEventsByEmployeeName(employeeName);
        return eventEmployees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
