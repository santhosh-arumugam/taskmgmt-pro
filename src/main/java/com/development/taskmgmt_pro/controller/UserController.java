package com.development.taskmgmt_pro.controller;
import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO response = userService.createUser(createUserDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
