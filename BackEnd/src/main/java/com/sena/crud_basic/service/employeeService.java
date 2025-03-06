package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.employeeDTO;
import com.sena.crud_basic.model.employee;
import com.sena.crud_basic.repository.Iemployee;

@Service
public class employeeService {

    @Autowired
    private Iemployee data;

    public void save(employeeDTO employeeDTO) {
        employee employee = convertToModel(employeeDTO);
        data.save(employee);
    }

    public employeeDTO convertToDTO(employee employee) {
        return new employeeDTO(
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getAddress(),
                employee.getPhone_number());
    }

    public employee convertToModel(employeeDTO employeeDTO) {
        return new employee(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getAddress(),
                employeeDTO.getPhoneNumber());
    }
}
