package com.example.blogapp.blogapp.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.blogapp.blogapp.users.dtos.CreateUserRequest;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    
    @Autowired
    UsersService userService;

    @MockBean
    UsersRepository usersRepository;

    @Test
    void can_create_users() {
        var user = userService.createUser(new CreateUserRequest(
                    "ashok",
                    "ashok123",
                    "ashok@gmail.com"
        ));
        Mockito.verify(usersRepository).save(user);
        Assertions.assertNotNull(userService);
        Assertions.assertEquals("ashok", user.getUsername());
    }
}
