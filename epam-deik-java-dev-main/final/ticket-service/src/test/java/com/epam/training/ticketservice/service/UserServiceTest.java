package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.hamcrest.core.IsEqual;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void createAdminIfNotPresent() {
        User admin = new User(1,"admin","admin",true,false);
        List<User> userList = new ArrayList<>();
        userList.add(admin);
        when(userRepository.getById(1)).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(admin);
        when(userRepository.findAll()).thenReturn(userList);

        userService.createAdminIfNotPresent();

        Assert.notEmpty(StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));
    }

    @Test
    void dontCreateAdminIfPresent() {
        User admin = new User(1,"admin","admin",true,false);
        when(userRepository.save(any(User.class))).thenReturn(admin);
        when(userRepository.getById(1)).thenReturn(admin);

        userService.createAdminIfNotPresent();

        verify(userRepository, atMostOnce()).save(any(User.class));
    }

    @Test
    void adminLogin() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        boolean result = userService.adminLogin("admin","admin");

        Assert.isTrue(result);
    }

    @Test
    void signOutIfAdminSignedIn() {
        User user = new User(1,"admin","admin",true,true);
        ArrayList<User> userIterable = new ArrayList<>();
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        String result = userService.signOut();

        assertEquals(result,"Sign out successful with privileged account admin");
    }

    @Test
    void signOutIfNoUsers() {
        ArrayList<User> userIterable = new ArrayList<>();
        doReturn(userIterable).when(userRepository).findAll();

        String result = userService.signOut();

        assertEquals(result,"You do not have privilege to use this command");
    }

    @Test
    void getLoggedInUser() {
        User admin = new User(1,"admin","admin",true,true);
        User user = new User(2,"b","b",false,false);
        ArrayList<User> userIterable = new ArrayList<>();
        userIterable.add(admin);
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(admin);
        when(userRepository.getById(1)).thenReturn(admin);
        doReturn(userIterable).when(userRepository).findAll();

        var result = userService.getLoggedInUser();

        assertEquals(result,admin);
    }

    @Test
    void isLoggedInUserAdmin() {
        User admin = new User(1,"admin","admin",true,true);
        User user = new User(2,"b","b",false,false);
        ArrayList<User> userIterable = new ArrayList<>();
        userIterable.add(admin);
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(admin);
        when(userRepository.getById(1)).thenReturn(admin);
        doReturn(userIterable).when(userRepository).findAll();

        var result = userService.isLoggedInUserAdmin();

        assertEquals(result,true);
    }
}