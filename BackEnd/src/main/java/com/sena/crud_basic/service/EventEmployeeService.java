package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.EventEmployeeDTO;
import com.sena.crud_basic.model.EventEmployee;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.model.employee;
import com.sena.crud_basic.repository.IEventEmployee;

@Service
public class EventEmployeeService {

    @Autowired
    private IEventEmployee data;

    public void save(EventEmployeeDTO eventEmployeeDTO) {
        EventEmployee eventEmployee = convertToModel(eventEmployeeDTO);
        data.save(eventEmployee);
    }

    public EventEmployeeDTO convertToDTO(EventEmployee eventEmployee) {
        return new EventEmployeeDTO(
                eventEmployee.getIdEventEmployee(),
                eventEmployee.getEvent(),
                eventEmployee.getEmployee()
        );
    }

    public EventEmployee convertToModel(EventEmployeeDTO eventEmployeeDTO) {
        Events event = eventEmployeeDTO.getEvent();
        employee employee = eventEmployeeDTO.getEmployee();
        return new EventEmployee(event, employee);
    }
}
