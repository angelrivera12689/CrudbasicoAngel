package com.sena.crud_basic.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.model.RecoveryRequest;
import com.sena.crud_basic.service.RecoveryRequestService;

@RestController
@RequestMapping("/api/v1/public/recovery-requests")
public class RecoveryRequestController {

    @Autowired
    private RecoveryRequestService recoveryRequestService;

    // Listar todas las solicitudes de recuperación
    @GetMapping
    public ResponseEntity<List<RecoveryRequest>> getAll() {
        return ResponseEntity.ok(recoveryRequestService.findAll());
    }

    // Buscar solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<RecoveryRequest> rr = recoveryRequestService.findById(id);
        return rr.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada"));
    }

    // Buscar solicitud por token
    @GetMapping("/by-token/{token}")
    public ResponseEntity<?> getByToken(@PathVariable String token) {
        Optional<RecoveryRequest> rr = recoveryRequestService.findByToken(token);
        return rr.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada"));
    }

    // Buscar solicitudes por ID de usuario
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<RecoveryRequest>> getByUser(@PathVariable int userID) {
        return ResponseEntity.ok(recoveryRequestService.findByUserID(userID));
    }

    // Crear solicitud de recuperación por email
  @PostMapping("/create")
public ResponseEntity<?> createByEmail(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String response = recoveryRequestService.createRecoveryRequestByEmail(email);
    if (response.length() > 30) {
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}


    // Eliminar solicitud por id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        String response = recoveryRequestService.deleteById(id);
        if (response.equals("Solicitud de recuperación eliminada")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Marcar solicitud como usada (opcional)
    @PostMapping("/use/{token}")
    public ResponseEntity<String> markAsUsed(@PathVariable String token) {
        String response = recoveryRequestService.markAsUsed(token);
        if (response.equals("Solicitud marcada como usada")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Cambiar contraseña vía token (nuevo password)
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String token, @RequestParam String newPassword) {
        String response = recoveryRequestService.changePasswordByToken(token, newPassword);
        if (response.equals("Contraseña actualizada correctamente")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
