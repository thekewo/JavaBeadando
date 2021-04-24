package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {

    private RoomService roomService;
    private UserService userService;

    @Autowired
    public RoomController(
            RoomService roomService,
            UserService userService)
    {this.roomService = roomService;
     this.userService = userService;
    }

    public void createRoom(String name, int rows, int columns)
    {
        if(userService.isLoggedInUserAdmin())
        {
            roomService.createRoom(name, rows, columns);
        }
    }

    public void updateRoom(String name, int rows, int columns)
    {
        if(userService.isLoggedInUserAdmin())
        {
            roomService.updateRoom(name, rows, columns);
        }
    }

    public void deleteRoom(String name)
    {
        if(userService.isLoggedInUserAdmin())
        {
            var room = roomService.findByName(name);
            if(room != null)roomService.deleteRoom(room.getName());
        }
    }

    public List<Room> getAllRooms()
    {
        return roomService.getAllRooms();
    }
}
