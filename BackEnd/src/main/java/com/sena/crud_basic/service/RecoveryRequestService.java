package com.sena.crud_basic.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.crud_basic.model.RecoveryRequest;
import com.sena.crud_basic.model.User;
import com.sena.crud_basic.repository.IRecoveryRequest;
import com.sena.crud_basic.repository.IUser;

@Service
public class RecoveryRequestService {

    @Autowired
    private IRecoveryRequest recoveryRequestRepository;

    @Autowired
    private IUser userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService EmailService;

    // Listar todas las solicitudes de recuperación
    public List<RecoveryRequest> findAll() {
        return recoveryRequestRepository.findAll();
    }

    // Buscar solicitud por ID
    public Optional<RecoveryRequest> findById(int id) {
        return recoveryRequestRepository.findById(id);
    }

    // Buscar solicitud por token
    public Optional<RecoveryRequest> findByToken(String token) {
        return recoveryRequestRepository.findByToken(token);
    }

    // Buscar solicitudes por ID de usuario
    public List<RecoveryRequest> findByUserID(int userID) {
        return recoveryRequestRepository.findByUserID_UserID(userID);
    }

    // Generar token seguro
    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    // Crear solicitud de recuperación por email
    @Transactional
public String createRecoveryRequestByEmail(String email) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (!userOptional.isPresent()) {
        return "Usuario no encontrado";
    }

    User user = userOptional.get();
    String token = generateSecureToken();
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expiresAt = now.plusHours(1);

    RecoveryRequest recoveryRequest = new RecoveryRequest(
        0, user, token, false, expiresAt, now
    );

    try {
        recoveryRequestRepository.save(recoveryRequest);
        EmailService.sendRecoveryToken(email, token); // <--- aquí envías el email
        return token;
    } catch (DataAccessException e) {
        return "Error de base de datos al guardar la solicitud";
    } catch (Exception e) {
        return "Error inesperado al guardar la solicitud";
    }
}

    // Eliminar solicitud de recuperación por ID
    @Transactional
    public String deleteById(int id) {
        Optional<RecoveryRequest> recoveryRequest = recoveryRequestRepository.findById(id);
        if (!recoveryRequest.isPresent()) {
            return "Solicitud de recuperación no encontrada";
        }
        try {
            recoveryRequestRepository.deleteById(id);
            return "Solicitud de recuperación eliminada";
        } catch (DataAccessException e) {
            return "Error de base de datos al eliminar la solicitud";
        } catch (Exception e) {
            return "Error inesperado al eliminar la solicitud";
        }
    }

    // Marcar solicitud como usada por token
    @Transactional
    public String markAsUsed(String token) {
        Optional<RecoveryRequest> recoveryRequestOptional = recoveryRequestRepository.findByToken(token);
        if (!recoveryRequestOptional.isPresent()) {
            return "Solicitud de recuperación no encontrada";
        }
        RecoveryRequest recoveryRequest = recoveryRequestOptional.get();

        if (recoveryRequest.isUsed()) {
            return "Token ya fue usado";
        }
        if (recoveryRequest.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token expirado";
        }

        recoveryRequest.setUsed(true);
        recoveryRequestRepository.save(recoveryRequest);
        return "Solicitud marcada como usada";
    }

    // Cambiar contraseña vía token de recuperación (sin pedir antigua)
    @Transactional
    public String changePasswordByToken(String token, String newPassword) {
        Optional<RecoveryRequest> recoveryRequestOpt = recoveryRequestRepository.findByToken(token);
        if (!recoveryRequestOpt.isPresent()) {
            return "Token inválido";
        }

        RecoveryRequest request = recoveryRequestOpt.get();

        if (request.isUsed()) {
            return "Token ya usado";
        }
        if (request.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token expirado";
        }

        User user = request.getUserID();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Marcar token como usado
        request.setUsed(true);
        recoveryRequestRepository.save(request);

        return "Contraseña actualizada correctamente";
    }
}
