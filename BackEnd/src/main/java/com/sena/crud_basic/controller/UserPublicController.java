package com.sena.crud_basic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.ResponseLogin;
import com.sena.crud_basic.DTO.UserDTO;
import com.sena.crud_basic.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public/users")
@RequiredArgsConstructor
public class UserPublicController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO) {
        ResponseDTO response = userService.register(userDTO);
        return response.getStatus().equals("success")
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseLogin("error", "Email y contraseña son requeridos", null));
        }
        ResponseLogin response = userService.login(userDTO.getEmail(), userDTO.getPassword());
        return response.getStatus().equals("success")
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Puedes agregar aquí endpoints futuros como "forgot password", "confirm email", etc.
    // @PostMapping("/forgot")
    // public ResponseEntity<ResponseDTO> forgotPassword(@RequestBody ForgotPasswordDTO forgotDTO) {
    //      // Lógica de recuperación de contraseña
    // }
}