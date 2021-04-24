package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {

    private MovieService movieService;
    private UserService userService;

    @Autowired
    public MovieController(
            MovieService movieService,
            UserService userService)
    {
        this.movieService = movieService;
        this.userService = userService;
    }

    public List<Movie> getAllMovies()
    {
        return movieService.getAllMovies();
    }

    public void createMovie(String name, String type, int movie_lenght)
    {
        if(userService.isLoggedInUserAdmin())
        {
            movieService.createMovie(name, type, movie_lenght);
        }
    }

    public void updateMovie(String name, String type, int movie_lenght)
    {
        if(userService.isLoggedInUserAdmin())
        {
            movieService.updateMovie(name, type, movie_lenght);
        }
    }

    public void deleteMovie(String name)
    {
        if(userService.isLoggedInUserAdmin())
        {
            movieService.deleteMovie(name);
        }
    }

    public Movie getMovieByName(String name)
    {
        return movieService.findByName(name);
    }
}
