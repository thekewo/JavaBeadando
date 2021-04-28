package com.epam.training.ticketservice;

import com.epam.training.ticketservice.controller.MovieController;
import com.epam.training.ticketservice.controller.RoomController;
import com.epam.training.ticketservice.controller.ScreeningController;
import com.epam.training.ticketservice.controller.UserController;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class MyCommandsTest {

    @Mock
    private MovieController mockMovieController;
    @Mock
    private UserController mockUserController;
    @Mock
    private RoomController mockRoomController;
    @Mock
    private ScreeningController mockScreeningController;

    private MyCommands myCommandsUnderTest;

    private AutoCloseable mockitoCloseable;

    @BeforeEach
    void setUp() {
        mockitoCloseable = openMocks(this);
        myCommandsUnderTest = new MyCommands(mockMovieController, mockUserController, mockRoomController, mockScreeningController);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockitoCloseable.close();
    }

    @Test
    void testSignInPrivileged() {
        // Setup
        when(mockUserController.adminLogin("username", "password"))
                .thenReturn("Login successful with privileged account username");

        // Run the test
        final String result = myCommandsUnderTest.signInPrivileged("username", "password");

        // Verify the results
        assertEquals("Login successful with privileged account username", result);
    }

    @Test
    void testSignOutPrivileged() {
        // Setup
        when(mockUserController.adminLogin("username", "password"))
                .thenReturn("Login successful with privileged account username");
        when(mockUserController.signOut()).thenReturn("Sign out successful with privileged account username");

        // Run the test
        final String result = myCommandsUnderTest.signOutPrivileged();

        // Verify the results
        assertEquals("Sign out successful with privileged account username", result);
    }

    @Test
    void testDescribeAccount() {
        // Setup
        when(mockUserController.adminLogin("username", "password"))
                .thenReturn("Login successful with privileged account username");
        when(mockUserController.describeAccount()).thenReturn("Signed in with privileged account username");

        // Run the test
        final String result = myCommandsUnderTest.describeAccount();

        // Verify the results
        assertEquals("Signed in with privileged account username", result);
    }

    @Test
    void testCreateMovie() {
        // Setup

        // Run the test
        myCommandsUnderTest.createMovie("name", "type", 0);

        // Verify the results
        verify(mockMovieController).createMovie("name", "type", 0);
    }

    @Test
    void testUpdateMovie() {
        // Setup

        // Run the test
        myCommandsUnderTest.updateMovie("name", "type", 0);

        // Verify the results
        verify(mockMovieController).updateMovie("name", "type", 0);
    }

    @Test
    void testDeleteMovie() {
        // Setup

        // Run the test
        myCommandsUnderTest.deleteMovie("name");

        // Verify the results
        verify(mockMovieController).deleteMovie("name");
    }

    @Test
    void testListMovies() {
        // Setup
        when(mockMovieController.getAllMovies()).thenReturn(List.of(new Movie("name", "type", 0)));

        // Run the test
        myCommandsUnderTest.listMovies();

        // Verify the results
    }

    @Test
    void testListMovies_MovieControllerReturnsNoItems() {
        // Setup
        when(mockMovieController.getAllMovies()).thenReturn(Collections.emptyList());

        // Run the test
        myCommandsUnderTest.listMovies();

        // Verify the results
    }

    @Test
    void testCreateRoom() {
        // Setup

        // Run the test
        myCommandsUnderTest.createRoom("name", 0, 0);

        // Verify the results
        verify(mockRoomController).createRoom("name", 0, 0);
    }

    @Test
    void testUpdateRoom() {
        // Setup

        // Run the test
        myCommandsUnderTest.updateRoom("name", 0, 0);

        // Verify the results
        verify(mockRoomController).updateRoom("name", 0, 0);
    }

    @Test
    void testDeleteRoom() {
        // Setup

        // Run the test
        myCommandsUnderTest.deleteRoom("name");

        // Verify the results
        verify(mockRoomController).deleteRoom("name");
    }

    @Test
    void testListRooms() {
        // Setup
        when(mockRoomController.getAllRooms()).thenReturn(List.of(new Room("name", 0, 0)));

        // Run the test
        myCommandsUnderTest.listRooms();

        // Verify the results
    }

    @Test
    void testListRooms_RoomControllerReturnsNoItems() {
        // Setup
        when(mockRoomController.getAllRooms()).thenReturn(Collections.emptyList());

        // Run the test
        myCommandsUnderTest.listRooms();

        // Verify the results
    }

    @Ignore
    @Test
    void testCreateScreening() {
        // Setup
        when(mockUserController.adminLogin("username", "password")).thenReturn("result");
        myCommandsUnderTest.createMovie("movieName", "type", 0);
        myCommandsUnderTest.createRoom("roomName", 10, 10);

        // Run the test

        myCommandsUnderTest.createScreening("movieName", "roomName", "2020-01-01 00:00");

        // Verify the results
        verify(mockScreeningController).createScreening("movieName", "roomName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Ignore
    @Test
    void testDeleteScreening() {
        // Setup

        // Run the test
        myCommandsUnderTest.deleteScreening("movieName", "roomName", "2020-01-01 00:00");

        // Verify the results
        verify(mockScreeningController).deleteScreening("movieName", "roomName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Ignore
    @Test
    void testListScreening() {
        // Setup

        // Configure ScreeningController.getAllScreening(...).
        final List<Screening> screeningList = List.of(new Screening("movieName", "roomName", new Date(2020, Calendar.JANUARY, 1)));
        when(mockScreeningController.getAllScreening()).thenReturn(screeningList);

        when(mockMovieController.getMovieByName("movieName")).thenReturn(new Movie("movieName", "type", 0));

        // Run the test
        myCommandsUnderTest.listScreening();

        // Verify the results
    }

    @Test
    void testListScreening_ScreeningControllerReturnsNoItems() {
        // Setup
        when(mockScreeningController.getAllScreening()).thenReturn(Collections.emptyList());
        when(mockMovieController.getMovieByName("name")).thenReturn(new Movie("name", "type", 0));

        // Run the test
        myCommandsUnderTest.listScreening();

        // Verify the results
    }
}
