package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserService userService;

    UserController userController;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
        userController = new UserController(userService);
    }

    @Test
    void adminLoginWithCorrectCredentials() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        var result = userController.adminLogin("admin","admin");

        assertEquals("Login successful with privileged account admin",result);
    }

    @Test
    void adminLoginWithWrongCredentials() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        var result = userController.adminLogin("a","a");

        assertEquals("Login failed due to incorrect credentials",result);
    }

    @Test
    void signOutWhileAdminSignedIn() {
        User user = new User(1,"admin","admin",true,true);
        List<User> userIterable = new ArrayList<>();
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        userController.adminLogin("admin","admin");
        var result = userController.signOut();

        assertEquals("Sign out successful with privileged account admin",result);
    }

    @Test
    void signOutWhileAdminNotSignedIn() {
        User user = new User(1,"admin","admin",true,true);
        List<User> userIterable = new ArrayList<>();
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        var result = userController.signOut();

        assertEquals("You do not have privilege to use this command",result);
    }

    @Test
    void describeAccountWithAdminSignedIn() {
        User user = new User(1,"admin","admin",true,true);
        List<User> userIterable = new ArrayList<>();
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        userController.adminLogin("admin","admin");
        var result = userController.describeAccount();

        assertEquals("Signed in with privileged account 'admin'",result);
    }

    @Test
    void describeAccountWithAdminNotSignedIn() {
        User user = new User(1,"admin","admin",true,true);
        List<User> userIterable = new ArrayList<>();
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        var result = userController.describeAccount();

        assertEquals("You are not signed in",result);
    }

    @Test
    void describeAccountWhenLoggedInUserIsNotAdmin() {
        User user = new User(1,"admin","admin",false,true);
        List<User> userIterable = new ArrayList<>();
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",false,true));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        var result = userController.describeAccount();

        assertEquals("You are not signed in",result);
    }

    @Test
    void isLoggedInUserIsAdmin() {
        User user = new User(1,"admin","admin",true,true);
        List<User> userIterable = new ArrayList<>();
        userIterable.add(user);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        userController.adminLogin("admin","admin");
        var result = userController.isLoggedInUserAdmin();

        assertEquals(true, result);
    }

    @Test
    void isLoggedInUserIsNotAdmin() {
        User user = new User(1,"admin","admin",true,true);
        List<User> userIterable = new ArrayList<>();
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
        doReturn(userIterable).when(userRepository).findAll();

        var result = userController.isLoggedInUserAdmin();

        assertEquals(false, result);
    }
}