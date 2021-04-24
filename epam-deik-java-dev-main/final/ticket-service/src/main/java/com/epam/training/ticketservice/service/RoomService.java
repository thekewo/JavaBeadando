package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findByName(String name)
    {
        return roomRepository.findByName(name);
    }

    @Transactional
    public void createRoom(String name, int rows, int columns)
    {
        roomRepository.save(new Room(name, rows, columns));
    }

    @Transactional
    public void updateRoom(String name, int rows, int columns)
    {
        Room room = roomRepository.findByName(name);
        room.setName(name);
        room.setRows(rows);
        room.setColumns(columns);
        roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(String name)
    {
        roomRepository.delete(roomRepository.findByName(name));
    }

    @Transactional
    public List<Room> getAllRooms()
    {
        List<Room> roomList = new ArrayList<>();
        roomRepository.findAll().forEach(room -> roomList.add(room));
        return roomList;
    }
}
