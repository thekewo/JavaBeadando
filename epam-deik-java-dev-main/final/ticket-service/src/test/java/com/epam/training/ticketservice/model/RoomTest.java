package com.epam.training.ticketservice.model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room roomUnderTest;

    @BeforeEach
    void setUp() {
        roomUnderTest = new Room("r", 10, 10);
    }


    @Test
    void Room()
    {
        Room room = new Room();
        room.setName("a");
        Assert.assertEquals("a", room.getName());
    }


    @Test
    void getName() {Assert.assertEquals("r", roomUnderTest.getName());
    }

    @Test
    void setName() {
        roomUnderTest.setName("a");
        Assert.assertEquals("a", roomUnderTest.getName());
    }

    @Test
    void getRows() {Assert.assertEquals(10, roomUnderTest.getRows());
    }

    @Test
    void setRows() {
        roomUnderTest.setRows(11);
        Assert.assertEquals(11, roomUnderTest.getRows());
    }

    @Test
    void getColumns() {Assert.assertEquals(10, roomUnderTest.getColumns());
    }

    @Test
    void setColumns() {
        roomUnderTest.setColumns(12);
        Assert.assertEquals(12, roomUnderTest.getColumns());
    }
}