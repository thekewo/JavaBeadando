package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void createAdminIfNotPresent() {
    }

    @Test
    void adminLogin() {
        User user = new User(1,"test","",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"test","",true,false));
        when(userRepository.findById(1).get()).thenReturn(user);

        boolean result = userService.adminLogin("admin","admin");

        Assert.isTrue(result);
    }

    @Test
    void signOut() {
    }

    @Test
    void getLoggedInUser() {
    }

    @Test
    void isLoggedInUserAdmin() {
    }
}