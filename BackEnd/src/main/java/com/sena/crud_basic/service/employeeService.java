package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.employeeDTO;
import com.sena.crud_basic.model.employee;
import com.sena.crud_basic.repository.Iemployee;

@Service
public class employeeService {

    @Autowired
    private Iemployee data;

  // ✅ Método para registrar un empleado con validaciones
    public ResponseDTO save(employeeDTO employeeDTO) {
        // Validar que el nombre tenga entre 1 y 100 caracteres
        if (employeeDTO.getFirstName().length() < 1 || employeeDTO.getFirstName().length() > 100) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El primer nombre debe estar entre 1 y 100 caracteres"
            );
        }

        // Validar que el apellido tenga entre 1 y 100 caracteres
        if (employeeDTO.getLastName().length() < 1 || employeeDTO.getLastName().length() > 100) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El apellido debe estar entre 1 y 100 caracteres"
            );
        }

        // Validar el número de teléfono (debe tener 10 dígitos y solo números)
        if (employeeDTO.getPhoneNumber().length() != 10 || !employeeDTO.getPhoneNumber().matches("[0-9]+")) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El número de teléfono debe tener 10 dígitos y solo contener números"
            );
        }

        // Convertir el DTO a modelo
        employee employee = convertToModel(employeeDTO);

        // Guardar en la base de datos
        data.save(employee);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Empleado guardado exitosamente"
        );
    }

    // ✅ Método para obtener todos los empleados
    public List<employee> findAll() {
        return data.findAll();
    }

    // ✅ Método para buscar un empleado por ID
    public Optional<employee> findById(int id) {
        return data.findById(id);
    }

    // ✅ Método para eliminar un empleado por ID
    public ResponseDTO deleteEmployee(int id) {
        Optional<employee> employee = findById(id);
        if (!employee.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El empleado no existe"
            );
        }

        // Si existe, lo eliminamos
        data.deleteById(id);
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Empleado eliminado correctamente"
        );
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
