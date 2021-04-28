package com.epam.training.ticketservice;

import com.epam.training.ticketservice.controller.MovieController;
import com.epam.training.ticketservice.controller.RoomController;
import com.epam.training.ticketservice.controller.ScreeningController;
import com.epam.training.ticketservice.controller.UserController;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@ShellComponent
public class MyCommands {

    private MovieController movieController;
    private UserController userController;
    private RoomController roomController;
    private ScreeningController screeningController;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    public MyCommands(
            MovieController movieController,
            UserController userController,
            RoomController roomController,
            ScreeningController screeningController)
    {
        this.movieController = movieController;
        this.userController = userController;
        this.roomController = roomController;
        this.screeningController = screeningController;
    }

//    @ShellMethod(value = "Exit application.", key = "exit")
//    public void exit() {
//        System.exit(0);
//    }

    @ShellMethod(value = "Login admin user.", key = "sign in privileged")
    public String signInPrivileged(String username, String password) {
        return userController.adminLogin(username,password);
    }

    @ShellMethod(value = "Sign out admin user.", key = "sign out")
    public String signOutPrivileged() {
        return userController.signOut();
    }

    @ShellMethod(value = "Describe account.", key = "describe account")
    public String describeAccount() {
        return userController.describeAccount();
    }

    @ShellMethod(value = "Create movie.", key = "create movie")
    public void createMovie(String name, String type, int movie_lenght) {
        movieController.createMovie(name, type, movie_lenght);
    }

    @ShellMethod(value = "Update movie.", key = "update movie")
    public void updateMovie(String name, String type, int movie_lenght) {
        movieController.updateMovie(name, type, movie_lenght);
    }

    @ShellMethod(value = "Delete movie.", key = "delete movie")
    public void deleteMovie(String name) {
        movieController.deleteMovie(name);
    }

    @ShellMethod(value = "List movies.", key = "list movies")
    public void listMovies() {

        List<Movie> movies = movieController.getAllMovies();
        if(movies.size() == 0)
        {
            System.out.println("There are no movies at the moment\n");
        }
        else {
            for (Movie movie : movies) {
                System.out.println(String.format("%s (%s, %s minutes)\n",
                        movie.getName(), movie.getType(),
                        movie.getMovie_length()));
            }
        }
    }

    @ShellMethod(value = "Create room.", key = "create room")
    public void createRoom(String name, int rows, int columns) {
        roomController.createRoom(name, rows, columns);
    }

    @ShellMethod(value = "Update room.", key = "update room")
    public void updateRoom(String name, int rows, int columns) {
        roomController.updateRoom(name, rows, columns);
    }

    @ShellMethod(value = "Delete room.", key = "delete room")
    public void deleteRoom(String name) {
        roomController.deleteRoom(name);
    }

    @ShellMethod(value = "List rooms.", key = "list rooms")
    public void listRooms() {

        List<Room> rooms = roomController.getAllRooms();
        if(rooms.size() == 0)
        {
            System.out.println("There are no rooms at the moment\n");
        }
        else {
            for (Room room : rooms) {
                System.out.println(String.format("Room %s with %d seats, %d rows and %d columns\n",
                        room.getName(), room.getRows()*room.getColumns(), room.getRows(), room.getColumns()));
            }
        }
    }

    @ShellMethod(value = "Create screening.", key = "create screening")
    public void createScreening(String movieName, String roomName, String date){
        try {
            Date parsedDate = formatter.parse(date);
            screeningController.createScreening(movieName, roomName, parsedDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @ShellMethod(value = "Delete screening.", key = "delete screening")
    public void deleteScreening(String movieName, String roomName, String date){
        try {
            Date parsedDate = formatter.parse(date);
            screeningController.deleteScreening(movieName, roomName, parsedDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @ShellMethod(value = "List screenings.", key = "list screenings")
    public void listScreening() {

        List<Screening> screenings = screeningController.getAllScreening();
        if(screenings.size() == 0)
        {
            System.out.println("There are no screenings");
        }
        else {
            for (Screening screening : screenings) {

                var wat = screening.getScreeningDate();
                var wat2 = formatter.format(screening.getScreeningDate());

                Movie movie = movieController.getMovieByName(screening.getMovieName());
                System.out.println(String.format("%s (%s, %d minutes), screened in room %s, at %s",
                        movie.getName(), movie.getType(),
                        movie.getMovie_length(), screening.getRoomName(),
                        formatter.format(screening.getScreeningDate())));
            }
        }
    }
}
