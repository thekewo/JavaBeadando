package com.epam.training.ticketservice.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {

    private Screening screeningUnderTest;

    @BeforeEach
    void setUp() {
        screeningUnderTest = new Screening("m", "r", new Date());
    }


    @Test
    void Screening()
    {
        Screening screening = new Screening();
        screening.setMovieName("a");
        Assert.assertEquals("a", screening.getMovieName());
    }

    @Test
    void getMovieName() {
        Assert.assertEquals("m", screeningUnderTest.getMovieName());
    }

    @Test
    void setMovieName() {
        screeningUnderTest.setMovieName("a");
        Assert.assertEquals("a", screeningUnderTest.getMovieName());
    }

    @Test
    void getRoomName() {
        Assert.assertEquals("r", screeningUnderTest.getRoomName());
    }

    @Test
    void setRoomName() {
        screeningUnderTest.setRoomName("a");
        Assert.assertEquals("a", screeningUnderTest.getRoomName());
    }

    @Test
    void getScreeningDate() {
        Assert.assertEquals(new Date(), screeningUnderTest.getScreeningDate());
    }

    @Test
    void setScreeningDate() {
        Date date = new Date();
        screeningUnderTest.setScreeningDate(date);
        Assert.assertEquals(date, screeningUnderTest.getScreeningDate());
    }

    @Test
    void addMinutes() {
        int movie_length = 60;
        int minutes = screeningUnderTest.getScreeningDate().getMinutes();
        screeningUnderTest.addMinutes(movie_length);
        Assert.assertEquals(minutes, screeningUnderTest.getScreeningDate().getMinutes());
    }
}