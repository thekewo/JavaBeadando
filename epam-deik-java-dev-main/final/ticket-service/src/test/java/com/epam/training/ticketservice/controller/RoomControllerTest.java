package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoomControllerTest {

    @Mock
    RoomService roomService;
    @Mock
    RoomRepository roomRepository;

    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

    RoomController roomController;

    User user = new User(1,"admin","admin",true,true);
    List<Room> roomList = new ArrayList<>();
    Room room = new Room("r1",10,10);

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        roomService = new RoomService(roomRepository);
        userService = new UserService(userRepository);
        roomController = new RoomController(roomService,userService);

        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));

        when(roomRepository.save(any(Room.class))).thenReturn(room);
    }

    @Test
    void createRoom() {
        roomList.add(room);
        when(userRepository.getById(1)).thenReturn(user);
        when(roomRepository.findAll()).thenReturn(roomList);
        userService.adminLogin("admin","admin");
        roomController.createRoom("r1",10,10);
        Assertions.assertTrue(roomService.getAllRooms().size() > 0);
    }

    @Test
    void updateRoom() {
        when(roomRepository.findAll()).thenReturn(roomList);
        roomController.createRoom("r1",10,10);
        Assertions.assertTrue(roomService.getAllRooms().size() == 0);
    }

    @Test
    void deleteRoom() {
        when(userRepository.getById(1)).thenReturn(user);
        when(roomRepository.findAll()).thenReturn(roomList);
        userService.adminLogin("admin","admin");
        roomController.createRoom("r1",10,10);
        roomController.deleteRoom("r1");
        Assertions.assertTrue(roomService.getAllRooms().size() == 0);
    }

    @Test
    void getAllRooms() {
        roomList.add(room);
        roomList.add(room);
        roomList.add(room);
        when(userRepository.getById(1)).thenReturn(user);
        when(roomRepository.findAll()).thenReturn(roomList);
        userService.adminLogin("admin","admin");
        roomController.createRoom("r1",10,10);
        roomController.createRoom("r2",10,10);
        roomController.createRoom("r3",10,10);
        Assertions.assertTrue(roomService.getAllRooms().size() == 3);
    }
}