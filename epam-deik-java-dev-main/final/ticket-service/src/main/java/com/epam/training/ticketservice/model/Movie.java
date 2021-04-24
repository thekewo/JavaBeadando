package com.epam.training.ticketservice.model;

import javax.persistence.*;

@Entity
public class Movie {
    @Id
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private int movie_length;

    public Movie(){};

    public Movie(String name, String type, int movie_lenght) {
        this.name = name;
        this.type = type;
        this.movie_length = movie_lenght;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(int movie_length) {
        this.movie_length = movie_length;
    }
}