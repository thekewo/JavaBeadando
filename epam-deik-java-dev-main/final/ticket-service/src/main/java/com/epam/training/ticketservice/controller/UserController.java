package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public String adminLogin(String username,String password)
    {
        return userService.adminLogin(username, password) ?
                String.format("Login successful with privileged account %s", username) : "Login failed due to incorrect credentials";
    }

    public String signOut()
    {
        return userService.signOut();
    }

    public String describeAccount()
    {
        String result;
        Optional<User> user = userService.getLoggedInUser();
        if(user.isPresent())
        {
            if(user.get().isAdmin())
                result = String.format("Signed in with privileged account '%s'", user.get().getUsername());
            else result = "You are not signed in";
        }
        else
        {
            result = "You are not signed in";
        }
        return result;
    }

    public boolean isLoggedInUserAdmin()
    {
        return userService.isLoggedInUserAdmin();
    }
}
