package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoomServiceTest {

    @Mock
    RoomRepository roomRepository;

    RoomService roomService;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        roomService = new RoomService(roomRepository);
    }

    @Test
    void findByName() {
        Room room = new Room("r",10,60);
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomRepository.findAll()).thenReturn(roomList);

        roomService.findByName("r");
        var result = roomService.getAllRooms();

        Assert.assertEquals(room, result.iterator().next());
    }

    @Test
    void createRoom() {
        Room room = new Room("r",10,60);
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomRepository.findAll()).thenReturn(roomList);

        roomService.createRoom("r",10,60);
        var result = roomService.getAllRooms();

        Assert.assertEquals(room, result.iterator().next());
    }

    @Test
    void updateRoom() {
        Room room = new Room("r",10,60);
        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomRepository.findAll()).thenReturn(roomList);
        when(roomRepository.findByName(any(String.class))).thenReturn(room);

        roomService.createRoom("r",10,60);
        roomService.updateRoom("r",20,20);
        var result = roomService.getAllRooms();

        Assert.assertEquals(20, result.iterator().next().getColumns());
        Assert.assertEquals(20, result.iterator().next().getRows());
        Assert.assertEquals("r", result.iterator().next().getName());
    }

    @Test
    void deleteRoom() {
        Room room = new Room("r",10,60);
        List<Room> roomList = new ArrayList<>();

        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomRepository.findAll()).thenReturn(roomList);

        roomService.createRoom("r",10,60);
        roomService.deleteRoom("r");
        var result = roomService.getAllRooms();

        Assert.assertEquals(result.size(), 0);
    }

    @Test
    void getAllRooms() {
        Room room1 = new Room("r1",10,60);
        Room room2 = new Room("r2",10,60);
        Room room3 = new Room("r3",10,60);
        List<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);

        when(roomRepository.save(any(Room.class))).thenReturn(room1);
        when(roomRepository.findAll()).thenReturn(roomList);

        var result = roomService.getAllRooms();

        Assert.assertEquals(result.size(), 3);
    }
}