package com.epam.training.ticketservice.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User(1,"name", "password",true,false);
    }

    @Test
    void Movie()
    {
        User user = new User();
        user.setUsername("a");
        Assert.assertEquals("a", user.getUsername());
    }

    @Test
    void getId() {
        Assert.assertEquals(1, userUnderTest.getId());
    }

    @Test
    void setId() {
        userUnderTest.setId(2);
        Assert.assertEquals(2, userUnderTest.getId());
    }

    @Test
    void getUsername() {
        Assert.assertEquals("name", userUnderTest.getUsername());
    }

    @Test
    void setUsername() {
        userUnderTest.setUsername("a");
        Assert.assertEquals("a", userUnderTest.getUsername());
    }

    @Test
    void getPassword() {
        Assert.assertEquals("password", userUnderTest.getPassword());
    }

    @Test
    void setPassword() {
        userUnderTest.setPassword("a");
        Assert.assertEquals("a", userUnderTest.getPassword());
    }

    @Test
    void isAdmin() {
        Assert.assertEquals(true, userUnderTest.isAdmin());
    }

    @Test
    void setAdmin() {
        userUnderTest.setAdmin(true);
        Assert.assertEquals(true, userUnderTest.isAdmin());
    }

    @Test
    void isLoggedIn() {
        Assert.assertEquals(false, userUnderTest.isLoggedIn());
    }

    @Test
    void setLoggedIn() {
        userUnderTest.setLoggedIn(true);
        Assert.assertEquals(true, userUnderTest.isLoggedIn());
    }
}