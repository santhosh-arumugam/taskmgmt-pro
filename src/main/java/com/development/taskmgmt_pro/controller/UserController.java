package com.development.taskmgmt_pro.controller;
import com.development.taskmgmt_pro.dto.*;
import com.development.taskmgmt_pro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


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

    @GetMapping
    public ResponseEntity<PagedResponseDTO<AllUsersResponseDTO>> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<AllUsersResponseDTO> usersPage = userService.findAllUsers(PageRequest.of(page, size));
        PagedResponseDTO<AllUsersResponseDTO> response = new PagedResponseDTO<>(
                usersPage.getContent(),
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseByIdDTO> findById(@PathVariable Long userId) {
        UserResponseByIdDTO response = userService.findUserById(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
