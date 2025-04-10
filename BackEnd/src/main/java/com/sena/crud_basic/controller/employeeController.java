package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.employeeDTO;
import com.sena.crud_basic.service.employeeService;
import com.sena.crud_basic.Resource.RateLimiterService;

@RestController
@RequestMapping("/api/v1/employee")
public class employeeController {

    @Autowired
    private employeeService employeeService;

    @Autowired
    private RateLimiterService rateLimiter;

    // ✅ Método para verificar límite
    private boolean isRateLimited() {
        return !rateLimiter.tryConsume();
    }

    // ✅ Registrar un nuevo empleado
    @PostMapping("/")
    public ResponseEntity<Object> registerEmployee(@RequestBody employeeDTO employeeDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

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
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    // ✅ Obtener un empleado por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var employee = employeeService.findById(id);
        if (!employee.isPresent()) {
            return new ResponseEntity<>("Empleado no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    // ✅ Eliminar un empleado por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = employeeService.deleteEmployee(id);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Filtrar empleados
    @GetMapping("/filter")
    public ResponseEntity<Object> filterEmployee(
            @RequestParam(required = false, name = "first_name") String first_name,
            @RequestParam(required = false, name = "last_name") String last_name,
            @RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "phone_number") String phone_number,
            @RequestParam(required = false, name = "status") Boolean status) {

        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        var employeeList = employeeService.filteremployee(first_name, last_name, address, phone_number, status);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    // ✅ Actualizar empleado
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestBody employeeDTO employeeDTO) {
        if (isRateLimited()) {
            return new ResponseEntity<>(new ResponseDTO("429", "🚫 Límite de peticiones alcanzado"), HttpStatus.TOO_MANY_REQUESTS);
        }

        ResponseDTO response = employeeService.update(id, employeeDTO);
        if (response.getStatus().equals(HttpStatus.OK.toString())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
