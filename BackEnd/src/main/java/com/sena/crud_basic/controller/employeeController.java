package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.employeeDTO;
import com.sena.crud_basic.service.employeeService;

@RestController
@RequestMapping("/api/v1/employee")
public class employeeController {

    @Autowired
    private employeeService employeeService;

    // Registrar un nuevo empleado
    @PostMapping("/")
    public ResponseEntity<Object> registerEmployee(@RequestBody employeeDTO employeeDTO) {
        ResponseDTO response = employeeService.save(employeeDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Obtener todos los empleados
    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    // ✅ Obtener un empleado por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable int id) {
        var employee = employeeService.findById(id);
        if (!employee.isPresent()) {
            return new ResponseEntity<>("Empleado no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    // ✅ Eliminar un empleado por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
        ResponseDTO response = employeeService.deleteEmployee(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<Object> filterEmployee(
            @RequestParam(required = false, name = "first_name") String first_name,
            @RequestParam(required = false, name = "last_name") String last_name,
            @RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "phone_number") String phone_number,
            @RequestParam(required = false, name = "status") Boolean status) {
    
        var employeeList = employeeService.filteremployee(first_name, last_name, address, phone_number, status);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestBody employeeDTO employeeDTO) {
        ResponseDTO response = employeeService.update(id, employeeDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}