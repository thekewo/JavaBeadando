package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserService{

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        createAdminIfNotPresent();
    }

    @Transactional
    public void createAdminIfNotPresent()
    {
        if(Objects.isNull(userRepository.getById(1)))
            userRepository.save(new User(1,"admin","admin",true,false));
    }

    @Transactional
    public boolean adminLogin(String username,String password)
    {
        User user = userRepository.getById(1);
        boolean valid = user.getUsername().equals(username) && user.getPassword().equals(password);
        user.setLoggedIn(valid);
        return valid;
    }

    @Transactional
    public String signOut()
    {
        String result;
        User user = getLoggedInUser();

        if(!Objects.isNull(user))
        {
            if (user.isAdmin())
            {
                user.setLoggedIn(false);
                result = String.format("Sign out successful with privileged account %s", user.getUsername());
            }
            else result = "You do not have privilege to use this command";
        }
        else
        {
            result = "You do not have privilege to use this command";
        }

        return result;
    }

    @Transactional
    public User getLoggedInUser()
    {
        var result = new User();
        var user = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(u -> u.isLoggedIn())
                .findFirst();
        if (user.isPresent()) result = user.get();
        else result = null;

        return result;
    }

    public boolean isLoggedInUserAdmin()
    {
        boolean result = false;
        User user = getLoggedInUser();
        if(!Objects.isNull(user))
        {
            result = user.isAdmin();
        }

        return result;
    }
}
