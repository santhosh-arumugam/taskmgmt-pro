package com.development.taskmgmt_pro.repository;

import com.development.taskmgmt_pro.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testSaveUser() {
        //Given
        User user = new User();
        user.setUserName("Santhosh5");
        user.setEmailId("santhosh5@gmail.com");

        //When
        User savedUser = userRepository.save(user);

        //Then
        assertNotNull(savedUser.getUserId(), "User should be available");
        assertEquals("Santhosh5", savedUser.getUserName(), "Username should match");
        assertEquals("santhosh5@gmail.com", savedUser.getEmailId(), "email ID should match");

        //Verify in database
        Optional<User> found = userRepository.findById(savedUser.getUserId());
        assertTrue(found.isPresent(), "User should be present in DB");
        assertEquals("Santhosh5", found.get().getUserName());
        assertEquals("santhosh5@gmail.com", found.get().getEmailId());

    }
}
