package com.epam.training.ticketservice.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private Movie movieUnderTest;

    @BeforeEach
    void setUp() {
        movieUnderTest = new Movie("name", "type", 0);
    }


    @Test
    void Movie()
    {
        Movie movie = new Movie();
        movie.setName("a");
        Assert.assertEquals("a", movie.getName());
    }

    @Test
    void getName() {
        Assert.assertEquals("name", movieUnderTest.getName());
    }

    @Test
    void setName() {
        movieUnderTest.setName("a");
        Assert.assertEquals("a", movieUnderTest.getName());
    }

    @Test
    void getType() {
        Assert.assertEquals("type", movieUnderTest.getType());
    }

    @Test
    void setType() {
        movieUnderTest.setType("a");
        Assert.assertEquals("a", movieUnderTest.getType());
    }

    @Test
    void getMovie_length() {
        Assert.assertEquals(0, movieUnderTest.getMovie_length());
    }

    @Test
    void setMovie_length() {
        movieUnderTest.setMovie_length(1);
        Assert.assertEquals(1, movieUnderTest.getMovie_length());
    }
}