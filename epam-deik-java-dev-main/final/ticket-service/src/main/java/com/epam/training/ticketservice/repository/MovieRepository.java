package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, String> {
    Movie findByName(String name);
}
