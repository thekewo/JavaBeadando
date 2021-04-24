package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(!userRepository.findById(1).isPresent())
            userRepository.save(new User(1,"admin","admin",true,false));
    }

    @Transactional
    public boolean adminLogin(String username,String password)
    {
        User user = userRepository.findById(1).get();
        boolean valid = user.getUsername().equals(username) && user.getPassword().equals(password);
        user.setLoggedIn(valid);
        return valid;
    }

    @Transactional
    public String signOut()
    {
        String result;
        Optional<User> user = getLoggedInUser();

        if(user.isPresent())
        {
            if (user.get().isAdmin())
            {
                user.get().setLoggedIn(false);
                result = String.format("Sign out successful with privileged account %s", user.get().getUsername());
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
    public Optional<User> getLoggedInUser()
    {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(u -> u.isLoggedIn())
                .findFirst();
    }

    public boolean isLoggedInUserAdmin()
    {
        boolean result = false;
        Optional<User> user = getLoggedInUser();
        if(user.isPresent())
        {
            result = user.get().isAdmin();
        }

        return result;
    }
}
