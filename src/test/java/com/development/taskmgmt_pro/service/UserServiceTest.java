package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.AllUsersResponseDTO;
import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseByIdDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.enums.JobRole;
import com.development.taskmgmt_pro.mapper.UserMapper;
import com.development.taskmgmt_pro.model.User;
import com.development.taskmgmt_pro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private CreateUserDTO createUserDTO;
    private UserResponseDTO userResponseDTO;
    private User user;
    private Pageable pageable;
    private AllUsersResponseDTO allUsersResponseDTO;
    private UserResponseByIdDTO userResponseByIdDTO;

    @BeforeEach
    void setUp() {

        createUserDTO = new CreateUserDTO();
        createUserDTO.setUserName("Santhosh_Kumar");
        createUserDTO.setEmailId("santhosh@gmail.com");
        createUserDTO.setFullName("Santhosh Kumar");
        createUserDTO.setJobRole("DEVELOPER");
        createUserDTO.setPassword("Password123");

        user = new User();
        user.setUserId(15L);
        user.setUserName("Santhosh_Kumar");
        user.setFullName("Santhosh Kumar");
        user.setEmailId("santhosh@gmail.com");
        user.setJobRole(JobRole.DEVELOPER);
        user.setPassword("$2a$12$021uVJBq9HX.6ex70iBLjeK0ywrH15DHJlR8MhWZtSo/baIE1rEHi");
        user.setRoles(Set.of("ROLE_DEVELOPER"));

        userResponseDTO = new UserResponseDTO(15L, "Santhosh_Kumar", "santhosh@gmail.com" , LocalDate.of(2025, 4, 29));

        userResponseByIdDTO = new UserResponseByIdDTO(15L,"Santhosh_Kumar", "Santhosh Kumar","santhosh@gmail.com", JobRole.DEVELOPER, LocalDate.of(2025, 4, 29));

        allUsersResponseDTO = new AllUsersResponseDTO(15L, "Santhosh_Kumar");

        pageable = PageRequest.of(0, 2);

    }

    @Test
    void createUser_success_userResponseDTO() {
        //Arrange
        when(userRepository.findByUserName("Santhosh_Kumar")).thenReturn(Optional.empty());
        when(userRepository.findByEmailId("santhosh@gmail.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(createUserDTO)).thenReturn(user);
        when(passwordEncoder.encode("Password123")).thenReturn("$2a$12$021uVJBq9HX.6ex70iBLjeK0ywrH15DHJlR8MhWZtSo/baIE1rEHi");
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userResponseDTO);

        //Act
        UserResponseDTO result = userService.createUser(createUserDTO);

        //Assert
        assertNotNull(result);
        assertEquals(15L, result.getUserId());
        assertEquals("Santhosh_Kumar", result.getUserName());
        assertEquals("santhosh@gmail.com", result.getEmailId());
        assertEquals(LocalDate.of(2025, 4, 29), result.getCreatedAt());
        verify(userRepository).findByUserName("Santhosh_Kumar");
        verify(userRepository).findByEmailId("santhosh@gmail.com");
        verify(passwordEncoder).encode("Password123");
        verify(userRepository).save(any());
    }
    
}
