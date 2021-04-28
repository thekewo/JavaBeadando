package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ScreeningControllerTest {

    @Mock
    MovieService movieService;
    @Mock
    MovieRepository movieRepository;

    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Mock
    RoomService roomService;
    @Mock
    RoomRepository roomRepository;

    @Mock
    ScreeningService screeningService;
    @Mock
    ScreeningRepository screeningRepository;

    ScreeningController screeningController;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieService(movieRepository);
        userService = new UserService(userRepository);
        roomService = new RoomService(roomRepository);
        screeningService = new ScreeningService(screeningRepository);
        screeningController = new ScreeningController(
                screeningService,
                movieService,
                roomService,
                userService);

        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);
    }

    @Test
    void createScreening() {
    }

    @Test
    void deleteScreening() {
        List<Screening> screeningList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        when(screeningService.findAll()).thenReturn(screeningList);

        screeningController.deleteScreening("m1","r1",date);
        var result = screeningController.getAllScreening();

        Assertions.assertTrue(result.size() == 0);
    }

    @Test
    void getAllScreening() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Screening screening1 = new Screening("m1","r1", date);
        date.setHours(1);
        Screening screening2 = new Screening("m2","r2",date);
        date.setHours(1);
        Screening screening3 = new Screening("m3","r3",date);
        List<Screening> screeningList = new ArrayList<>();
        screeningList.add(screening1);
        screeningList.add(screening2);
        screeningList.add(screening3);

        when(screeningService.findAll()).thenReturn(screeningList);

        var result = screeningController.getAllScreening();

        Assertions.assertTrue(result.size() == 3);
    }
}