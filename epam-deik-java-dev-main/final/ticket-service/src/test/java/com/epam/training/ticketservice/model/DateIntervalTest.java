package com.epam.training.ticketservice.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateIntervalTest {

    private DateInterval dateIntervalUnderTest;

    private Date date1 = new Date();
    private Date date2 = new Date();

    @BeforeEach
    void setUp() {
        dateIntervalUnderTest = new DateInterval(date1,date2);
    }


    @Test
    void Room()
    {
        DateInterval dateInterval = new DateInterval(dateIntervalUnderTest);
        Assert.assertEquals(date1, dateIntervalUnderTest.getStart());
        Assert.assertEquals(date2, dateIntervalUnderTest.getEnd());
    }

    @Test
    void getStart() {
        Assert.assertEquals(date1, dateIntervalUnderTest.getStart());
    }

    @Test
    void getEnd() {Assert.assertEquals(date2, dateIntervalUnderTest.getStart());
    }
}