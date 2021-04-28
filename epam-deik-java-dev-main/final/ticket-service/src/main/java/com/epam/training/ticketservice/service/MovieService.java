package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(
            MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    @Transactional
    public List<Movie> getAllMovies()
    {
        List<Movie> movieList = new ArrayList<>();
        movieRepository.findAll().forEach(movie -> movieList.add(movie));
        return movieList;
    }

    @Transactional
    public void createMovie(String name, String type, int movie_lenght)
    {
        if(StreamSupport.stream(movieRepository.findAll().spliterator(), false).filter(m -> m.getName().equals(name)).count() == 0)
        movieRepository.save(new Movie(name, type, movie_lenght));
    }

    @Transactional
    public void updateMovie(String name, String type, int movie_lenght)
    {
        Movie movie = movieRepository.findByName(name);
        movie.setType(type);
        movie.setMovie_length(movie_lenght);
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(String name)
    {
        Movie movie = movieRepository.findByName(name);
        movieRepository.delete(movie);
    }

    @Transactional
    public Movie findByName(String name)
    {
        return movieRepository.findByName(name);
    }

    @Transactional
    public Iterable<Movie> findAll()
    {
        return movieRepository.findAll();
    }
}
