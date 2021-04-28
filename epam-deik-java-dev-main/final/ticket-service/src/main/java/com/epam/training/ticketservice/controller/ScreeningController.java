package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ScreeningController {

    private ScreeningService screeningService;
    private MovieService movieService;
    private RoomService roomService;
    private UserService userService;

    @Autowired
    public ScreeningController(
            ScreeningService screeningService,
            MovieService movieService,
            RoomService roomService,
            UserService userService)
    {
        this.screeningService = screeningService;
        this.userService = userService;
        this.roomService = roomService;
        this.movieService = movieService;
    }

    public void createScreening(String movieName, String roomName, Date screeningDate)
    {
        Date screeningDateForCreate = new Date(screeningDate.getTime());
        Movie movie = movieService.findByName(movieName);
        if(userService.isLoggedInUserAdmin())
        {
            if(movie != null && roomService.findByName(roomName) != null)
            {
                if (isOverlapping(screeningDate,roomName , movie))
                {
                    System.out.println("There is an overlapping screening");
                }
                else if(isInBreak(screeningDate,roomName , movie))
                {
                    System.out.println("This would start in the break period after another screening in this room");
                } else
                {
                    screeningService.createScreening(movieName, roomName, screeningDateForCreate);
                }
            }
        }
    }

    private boolean isOverlapping(Date screeningDateStart, String roomName, Movie movie)
    {
        List<Interval> dateIntervals = new ArrayList<>();
        Date screeningDateEnd = new Date(screeningDateStart.getTime());
        screeningDateEnd.setMinutes(screeningDateEnd.getMinutes() + movie.getMovie_length());

        Interval interval = new Interval(new DateTime(screeningDateStart),new DateTime(screeningDateEnd));
        dateIntervals.add(interval);

        List<Movie> movies = StreamSupport
                .stream(movieService.findAll().spliterator(), false)
                .collect(Collectors.toList());

        List<Screening> screeningList = StreamSupport
                .stream(screeningService.findAll().spliterator(), false)
                .filter(s -> s.getRoomName().equals(roomName))
                .collect(Collectors.toList());

        for (Screening screening : screeningList
             ) {
            var optionalMovie = movies.stream().filter(m -> m.getName().equals(screening.getMovieName())).findFirst();
            if(optionalMovie.isPresent())
            {
                Date startDateToAdd = new Date(screening.getScreeningDate().getTime());
                Date endDateToAdd = screening.addMinutes(optionalMovie.get().getMovie_length());
                dateIntervals.add(new Interval(new DateTime(startDateToAdd),new DateTime(endDateToAdd)));
            }
        }

        var result = false;
        if(dateIntervals.size() > 1) result = overlapping(dateIntervals);

        return result;
    }

    private boolean overlapping(List<Interval> dateIntervals)
    {
        var result = false;
        var newInterval = dateIntervals.get(0);
        dateIntervals.remove(0);
        for (Interval interval : dateIntervals
        ){
            if(newInterval.overlaps(interval)) result = true;
        }
        return result;
    }

    private boolean isInBreak(Date screeningDateStart, String roomName, Movie movie)
    {
        List<Interval> dateIntervals = new ArrayList<>();
        Date screeningDateEnd = new Date(screeningDateStart.getTime());
        screeningDateEnd.setMinutes(screeningDateEnd.getMinutes() + movie.getMovie_length());

        Interval interval = new Interval(new DateTime(screeningDateStart),new DateTime(screeningDateEnd));
        dateIntervals.add(interval);

        List<Movie> movies = StreamSupport
                .stream(movieService.findAll().spliterator(), false)
                .collect(Collectors.toList());

        List<Screening> screeningList = StreamSupport
                .stream(screeningService.findAll().spliterator(), false)
                .filter(s -> s.getRoomName().equals(roomName))
                .collect(Collectors.toList());

        for (Screening screening : screeningList
        ) {
            var optionalMovie = movies.stream().filter(m -> m.getName().equals(screening.getMovieName())).findFirst();
            if(optionalMovie.isPresent())
            {
                Date startDateToAdd = new Date(screening.getScreeningDate().getTime());
                startDateToAdd.setMinutes(startDateToAdd.getMinutes() + optionalMovie.get().getMovie_length());

                Date endDateToAdd = new Date(startDateToAdd.getTime());
                endDateToAdd.setMinutes(endDateToAdd.getMinutes() + 10);

                dateIntervals.add(new Interval(new DateTime(startDateToAdd),new DateTime(endDateToAdd)));
            }
        }

        var result = false;
        if(dateIntervals.size() > 1) result = overlapping(dateIntervals);

        return result;
    }

    public void deleteScreening(String movieName, String roomName, Date screeningDate)
    {
        if(userService.isLoggedInUserAdmin())
        {
            screeningService.deleteScreening(movieName, roomName, screeningDate);
        }
    }

    public List<Screening> getAllScreening()
    {
        return StreamSupport
                .stream(screeningService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
