package com.sena.crud_basic.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_basic.DTO.userDTO;
import com.sena.crud_basic.service.userService;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/user")
public class userController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private userService userService;

    @PostMapping("/")
    public ResponseEntity<Object> registerUser(@RequestBody userDTO user) {
        userService.save(user);
        return new ResponseEntity<>("register OK", HttpStatus.OK);
    }

}
