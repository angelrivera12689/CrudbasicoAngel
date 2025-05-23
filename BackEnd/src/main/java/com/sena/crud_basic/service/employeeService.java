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
        return data.getListEmployeeActive();
    }

    // ✅ Método para buscar un empleado por ID
    public Optional<employee> findById(int id) {
        return data.findById(id);
    }

    // ✅ Método para eliminar un empleado por ID
    public ResponseDTO deleteEmployee(int id) {
        Optional<employee> employee = findById(id);
        if (!employee.isPresent()) {
            // Si no se encuentra el empleado, devolvemos una respuesta de error
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El registro no existe"
            );
        }
    
        // Cambiar el estado del empleado a false (eliminación lógica)
        employee.get().setStatus(false);
        data.save(employee.get());
    
        // Devolvemos una respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Empleado eliminado correctamente"
        );
    }

    public List<employee> filteremployee(String first_name, String last_name, String address, String phone_number, Boolean status) {
        return data.filteremployee(first_name, last_name, address, phone_number, status);
    }
    
    public ResponseDTO update(int id, employeeDTO employeeDTO) {
        Optional<employee> existingEmployee = findById(id);
        if (!existingEmployee.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El empleado no existe"
            );
        }
    
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
    
        // Validar si la URL de la imagen es válida (opcional, pero recomendable)
        if (employeeDTO.getImageUrl() != null && !employeeDTO.getImageUrl().isEmpty()) {
            if (!employeeDTO.getImageUrl().matches("^(http|https)://.*$")) {
                return new ResponseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "La URL de la imagen no es válida"
                );
            }
        }
    
        // Actualizar los datos del empleado existente
        employee employeeToUpdate = existingEmployee.get();
        employeeToUpdate.setFirst_name(employeeDTO.getFirstName());
        employeeToUpdate.setLast_name(employeeDTO.getLastName());
        employeeToUpdate.setAddress(employeeDTO.getAddress());
        employeeToUpdate.setPhone_number(employeeDTO.getPhoneNumber());
        employeeToUpdate.setImageUrl(employeeDTO.getImageUrl()); // <-- Aquí agregamos la imagen
    
        // Guardar los cambios
        data.save(employeeToUpdate);
    
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Empleado actualizado exitosamente"
        );
    }
    
    
    public employeeDTO convertToDTO(employee employee) {
        return new employeeDTO(
                employee.getFirst_name(),
                employee.getLast_name(),
                employee.getAddress(),
                employee.getPhone_number(),
                employee.getImageUrl());  // Incluir imageUrl
    }

    public employee convertToModel(employeeDTO employeeDTO) {
        return new employee(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getAddress(),
                employeeDTO.getPhoneNumber(),
                true,  // El estado inicial es true
                employeeDTO.getImageUrl());  // Incluir imageUrl
    }
}
