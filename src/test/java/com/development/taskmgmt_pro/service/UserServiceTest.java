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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    @Test
    void findAllUsers_success_pagedAllUsersResponseDTO() {
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<AllUsersResponseDTO> result = userService.findAllUsers(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        assertEquals(15L, result.getContent().getFirst().getUserId());
        assertEquals("Santhosh_Kumar", result.getContent().getFirst().getUserName());
        verify(userRepository).findAll(pageable);
    }

    @Test
    void findByUserId_existingId_returnUserResponseByIdDTO() {
        when(userRepository.findById(15L)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseByIdDTO);

        UserResponseByIdDTO result = userService.findUserById(15L);

        assertNotNull(result);
        assertEquals(15L, result.getUserId());
        assertEquals("Santhosh_Kumar", result.getUserName());
        assertEquals("Santhosh Kumar", result.getFullName());
        assertEquals("santhosh@gmail.com", result.getEmailId());
        assertEquals(JobRole.DEVELOPER, result.getJobRole());
        assertEquals(LocalDate.of(2025,4,29), result.getCreatedAt());
        verify(userRepository).findById(15L);
    }

    @Test
    void deleteById_existingId() {
        when(userRepository.findById(15L)).thenReturn(Optional.of(user));

        userService.deleteById(15L);

        verify(userRepository).findById(15L);
        verify(userRepository).deleteById(15L);
    }

    @Test
    void updateUserById_success_returnUserResponseByIdDTO() {
        when(userRepository.findById(15L)).thenReturn(Optional.of(user));
        when(userMapper.toUpdateEntity(createUserDTO, user)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseByIdDTO);

        UserResponseByIdDTO result = userService.updateUserById(15L, createUserDTO);

        assertNotNull(result);
        assertEquals(15L, result.getUserId());
        assertEquals("Santhosh_Kumar", result.getUserName());
        assertEquals("Santhosh Kumar", result.getFullName());
        assertEquals("santhosh@gmail.com", result.getEmailId());
        assertEquals(JobRole.DEVELOPER, result.getJobRole());
        assertEquals(LocalDate.of(2025,4,29), result.getCreatedAt());
        verify(userRepository).save(user);
    }
}