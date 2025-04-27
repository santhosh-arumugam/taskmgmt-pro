package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.*;
import com.development.taskmgmt_pro.exception.DuplicateUserException;
import com.development.taskmgmt_pro.exception.ResourceNotFoundException;
import com.development.taskmgmt_pro.mapper.UserMapper;
import com.development.taskmgmt_pro.model.User;
import com.development.taskmgmt_pro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserDTO dto) {

        if (userRepository.findByUserName(dto.getUserName()).isPresent()) {
            throw new DuplicateUserException("Username already exists");
        }

        if (userRepository.findByEmailId(dto.getEmailId()).isPresent()) {
            throw new DuplicateUserException("User email ID already exists");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword() != null ? dto.getPassword() : "DefaultPassword123"));
        user.setRoles(Set.of("ROLE_"+dto.getJobRole()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public Page<AllUsersResponseDTO> findAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> new AllUsersResponseDTO(user.getUserId(), user.getUserName()));
    }

    @Transactional
    public UserResponseByIdDTO findUserById(Long userId) {
        User fetchedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ID: "+userId+" does not exists"));
        return userMapper.toUserResponseDTO(fetchedUser);
    }

    @Transactional
    public void deleteById(Long userId) {
        User getUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User ID: "+userId+" does not exists"));
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserResponseByIdDTO updateUserById(Long userId, CreateUserDTO dto) {
        User getUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User ID: "+userId+" does not exist for any update"));
        User updatedUser = userRepository.save(userMapper.toUpdateEntity(dto, getUser));
        return userMapper.toUserResponseDTO(updatedUser);
    }
}
