package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.userDTO;
import com.sena.crud_basic.model.user;
import com.sena.crud_basic.repository.Iuser;

import java.time.LocalDateTime;

@Service
public class userService {

    /*
     * save
     * findAll
     * findById
     * Delete
     */
    /* establish connection with the interface */
    @Autowired
    private Iuser data;

    // register and update
    public void save(userDTO userDTO) {
        user userRegister = converToModel(userDTO);
        data.save(userRegister);
    }

    public userDTO convertToDTO(user user) {
        userDTO userdto = new userDTO(
                user.get_nombre(),
                user.getEmail(),
                user.get_contrasena(),
                user.get_telefono());
        return userdto;
    }

    public user converToModel(userDTO userDTO) {
        user user = new user(
                0,
                userDTO.getNombre(),
                userDTO.getEmail(),
                userDTO.getContrasena(),
                userDTO.getTelefono(),
                LocalDateTime.now());
        return user;
    }

}
