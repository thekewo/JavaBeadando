package com.epam.training.ticketservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Screening {

    @Id
    @Column
    private String movieName;
    @Column
    private String roomName;
    @Column
    private Date screeningDate;

    public Screening() {
    }

    public Screening(String movieName, String roomName, Date screeningDate) {
        this.movieName = movieName;
        this.roomName = roomName;
        this.screeningDate = screeningDate;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String name) {
        this.movieName = name;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(Date screeningDate) {
        this.screeningDate = screeningDate;
    }

    public Date addMinutes(int movie_length)
    {
        this.getScreeningDate().setMinutes(this.getScreeningDate().getMinutes() + movie_length);
        return this.getScreeningDate();
    }
}
