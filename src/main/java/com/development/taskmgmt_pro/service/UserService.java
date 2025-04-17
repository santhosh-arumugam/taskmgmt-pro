package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.exception.DuplicateUserException;
import com.development.taskmgmt_pro.model.User;
import com.development.taskmgmt_pro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserDTO dto) {
        Optional<User> existingUser = userRepository.findByUserName(dto.getUserName());
        if (existingUser.isPresent()) throw new DuplicateUserException("Username already exists");

        Optional<User> existingUserEmailId = userRepository.findByEmailId(dto.getEmailId());
        if (existingUserEmailId.isPresent()) throw new DuplicateUserException("User email ID already exists");

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmailId(dto.getEmailId());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getUserId(), savedUser.getUserName(), savedUser.getEmailId());

    }
}
