package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    MovieService movieService;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieService(movieRepository);
    }

    @Test
    void getAllMovies() {
        Movie movie1 = new Movie("m1","t1",60);
        Movie movie2 = new Movie("m2","t2",60);
        Movie movie3 = new Movie("m2","t2",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie1);
        when(movieRepository.findAll()).thenReturn(movieList);

        var result = movieService.getAllMovies();

        Assert.assertEquals(result.size(), 3);
    }

    @Test
    void createMovie() {
        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);

        movieService.createMovie("m1","t1",60);
        var result = movieService.getAllMovies();

        Assert.assertEquals(movie, result.iterator().next());
    }

    @Test
    void updateMovie() {
        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);
        when(movieRepository.findByName(any(String.class))).thenReturn(movie);

        movieService.createMovie("m1","t1",60);
        movieService.updateMovie("m1","t2",66);
        var result = movieService.getAllMovies();

        Assert.assertEquals("t2", result.iterator().next().getType());
        Assert.assertEquals(66, result.iterator().next().getMovie_length());
        Assert.assertEquals("m1", result.iterator().next().getName());
    }

    @Test
    void deleteMovie() {
        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);

        movieService.createMovie("m1","t1",60);
        movieService.deleteMovie("m1");
        var result = movieService.getAllMovies();

        Assert.assertEquals(0, result.size());
    }

    @Test
    void findByName() {
        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findByName(any(String.class))).thenReturn(movie);

        movieService.createMovie("m1","t1",60);
        var result = movieService.findByName(movie.getName());

        Assert.assertEquals("m1", movie.getName());
        Assert.assertEquals("t1", movie.getType());
        Assert.assertEquals(60, movie.getMovie_length());
    }

    @Test
    void findAll() {
        Movie movie1 = new Movie("m1","t1",60);
        Movie movie2 = new Movie("m2","t2",60);
        Movie movie3 = new Movie("m3","t3",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie1);
        when(movieService.findAll()).thenReturn(movieList);

        movieService.createMovie("m1","t1",60);
        movieService.createMovie("m2","t2",60);
        movieService.createMovie("m3","t3",60);
        var result = movieService.findAll();

        Assert.assertEquals(result.spliterator().estimateSize(), 3);
    }
}