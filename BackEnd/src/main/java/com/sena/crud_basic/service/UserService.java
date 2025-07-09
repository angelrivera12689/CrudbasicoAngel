package com.sena.crud_basic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Update these imports to match the actual package structure of your project
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.ResponseLogin;
import com.sena.crud_basic.DTO.UserDTO;
import com.sena.crud_basic.model.Role;
import com.sena.crud_basic.model.User;
import com.sena.crud_basic.repository.IRole;
import com.sena.crud_basic.repository.IUser;
import com.sena.crud_basic.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUser userRepository;
    private final IRole roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // Listar todos los usuarios
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    // Verificar si un usuario existe por ID
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    // Filtrar usuarios por email y contraseña
    public List<User> filterUser(String email, String password) {
        return userRepository.filterUser(email, password);
    }

    // Registro de usuario
    @Transactional
public ResponseDTO register(UserDTO userDTO) {
    // Obtener el primer rol disponible (puedes cambiar la lógica según tu necesidad)
    Optional<Role> roleEntity = roleRepository.findAll().stream().findFirst();

    if (!roleEntity.isPresent()) {
        return new ResponseDTO("error", "No hay roles configurados en la base de datos");
    }

    // Validar existencia de email
    if (userRepository.existsByEmail(userDTO.getEmail())) {
        return new ResponseDTO("error", "El correo electrónico ya está registrado");
    }

    // Validar formato de email
    if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        return new ResponseDTO("error", "El formato del correo electrónico no es válido");
    }

    // Validar longitud de contraseña
    if (userDTO.getPassword() == null || userDTO.getPassword().length() < 8) {
        return new ResponseDTO("error", "La contraseña debe tener al menos 8 caracteres");
    }

    try {
        // Crear entidad User con el rol por defecto
        User userEntity = convertToModel(userDTO, roleEntity.get());
        userRepository.save(userEntity);
        return new ResponseDTO("success", "Usuario registrado correctamente");
    } catch (DataAccessException e) {
        return new ResponseDTO("error", "Error de base de datos al guardar el usuario");
    } catch (Exception e) {
        return new ResponseDTO("error", "Error inesperado al guardar el usuario");
    }
}


    // Inicio de sesión
    public ResponseLogin login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return new ResponseLogin("error", "El correo electrónico no está registrado", null);
        }

        User userEntity = optionalUser.get();

        // Verificar la contraseña hasheada
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            return new ResponseLogin("error", "La contraseña es incorrecta", null);
        }

        // Construir UserDetails para JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities("ROLE_" + userEntity.getRoleID().getName())
                .build();

        // Generar token JWT usando JwtService
        String token = jwtService.generateToken(userDetails);

        // Devuelve status, message y token
        userEntity.setPassword(null);
        return new ResponseLogin("success", "Inicio de sesión exitoso", token);
    }

    // Eliminar usuario por ID (borrado físico)
    @Transactional
    public ResponseDTO deleteById(int id) {
        if (!userRepository.existsById(id)) {
            return new ResponseDTO("error", "Usuario no encontrado");
        }
        try {
            userRepository.deleteById(id);
            return new ResponseDTO("success", "Usuario eliminado correctamente");
        } catch (DataAccessException e) {
            return new ResponseDTO("error", "Error de base de datos al eliminar el usuario");
        } catch (Exception e) {
            return new ResponseDTO("error", "Error inesperado al eliminar el usuario");
        }
    }

    // Actualizar usuario por ID
    @Transactional
    public ResponseDTO update(int id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseDTO("error", "Usuario no encontrado");
        }
        try {
            User userEntity = userOptional.get();

            // Validar rol
            Optional<Role> roleEntity = roleRepository.findById(userDTO.getRoleID().getRoleID());
            if (!roleEntity.isPresent()) {
                return new ResponseDTO("error", "Rol no encontrado");
            }

            // Actualizar email si aplica
            if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
                if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    return new ResponseDTO("error", "El formato del correo electrónico no es válido");
                }
                userEntity.setEmail(userDTO.getEmail());
            }

            // Actualizar password si aplica
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                if (userDTO.getPassword().length() < 8) {
                    return new ResponseDTO("error", "La contraseña debe tener al menos 8 caracteres");
                }
                // Encriptar la nueva contraseña antes de guardarla
                userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            userEntity.setRoleID(roleEntity.get());
            userRepository.save(userEntity);

            return new ResponseDTO("success", "Usuario actualizado correctamente");
        } catch (DataAccessException e) {
            return new ResponseDTO("error", "Error de base de datos al actualizar el usuario");
        } catch (Exception e) {
            return new ResponseDTO("error", "Error inesperado al actualizar el usuario");
        }
    }

    // Convertir entidad a DTO (nunca expone la contraseña)
    public UserDTO convertToDTO(User userEntity) {
        return new UserDTO(
                userEntity.getEmail(),
                null,
                userEntity.getRoleID()
        );
    }
public String changePasswordAuthenticated(String email, String currentPassword, String newPassword) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isEmpty()) {
        return "Usuario no encontrado";
    }

    User user = optionalUser.get();

    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
        return "La contraseña actual es incorrecta";
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    return "Contraseña actualizada correctamente";
}

    // Convertir DTO a entidad, encriptando la contraseña
    public User convertToModel(UserDTO userDTO, Role role) {
        return new User(
                0, // ID autogenerado
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                role,
                LocalDateTime.now()
        );
    }
}