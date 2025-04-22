package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.AllUsersResponseDTO;
import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.exception.DuplicateUserException;
import com.development.taskmgmt_pro.mapper.UserMapper;
import com.development.taskmgmt_pro.model.User;
import com.development.taskmgmt_pro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserDTO dto) {

        if (userRepository.findByUserName(dto.getUserName()).isPresent()) {
            throw new DuplicateUserException("Username already exists");
        }

        if (userRepository.findByEmailId(dto.getEmailId()).isPresent()) {
            throw new DuplicateUserException("User email ID already exists");
        }
        User savedUser = userRepository.save(userMapper.toEntity(dto));
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public Page<AllUsersResponseDTO> findAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> new AllUsersResponseDTO(user.getUserId(), user.getUserName()));
    }
}
