package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.helper.CalendarEventOverlapHelper;
import com.epam.training.ticketservice.model.DateInterval;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
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
    private CalendarEventOverlapHelper calendarEventOverlapHelper;

    @Autowired
    public ScreeningController(
            ScreeningService screeningService,
            MovieService movieService,
            RoomService roomService,
            UserService userService,
            CalendarEventOverlapHelper calendarEventOverlapHelper)
    {
        this.screeningService = screeningService;
        this.userService = userService;
        this.roomService = roomService;
        this.movieService = movieService;
        this.calendarEventOverlapHelper = calendarEventOverlapHelper;
    }

    public void createScreening(String movieName, String roomName, Date screeningDate)
    {
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
                    screeningService.createScreening(movieName, roomName, screeningDate);
                }
            }
        }
    }

    private boolean isOverlapping(Date screeningDateStart, String roomName, Movie movie)
    {
        List<DateInterval> dateIntervals = new ArrayList<DateInterval>();

        Date screeningDateEnd = new Date(screeningDateStart.getTime());
        screeningDateEnd.setMinutes(screeningDateEnd.getMinutes() + movie.getMovie_length());

        DateInterval screeningDate = new DateInterval(screeningDateStart,screeningDateEnd);

        dateIntervals.add(screeningDate);

        List<Movie> movies = StreamSupport
                .stream(movieService.findAll().spliterator(), false)
                .collect(Collectors.toList());

        List<Screening> screeningList = StreamSupport
                .stream(screeningService.findAll().spliterator(), false)
                .filter(s -> s.getRoomName() == roomName)
                .collect(Collectors.toList());

        for (Screening screening : screeningList
             ) {
            var optionalMovie = movies.stream().filter(m -> m.getName() == screening.getMovieName()).findFirst();
            if(optionalMovie.isPresent())
            {
                dateIntervals.add(new DateInterval(screening.getScreeningDate(),screening.addMinutes(optionalMovie.get().getMovie_length())));
            }
        }

        return !calendarEventOverlapHelper.isIntervalsClean(dateIntervals);
    }

    private boolean isInBreak(Date screeningDateStart, String roomName, Movie movie)
    {
        List<DateInterval> dateIntervals = new ArrayList<DateInterval>();

        screeningDateStart.setMinutes(screeningDateStart.getMinutes() + movie.getMovie_length());
        Date screeningDateEnd = new Date(screeningDateStart.getTime());
        screeningDateEnd.setMinutes(screeningDateEnd.getMinutes() + 10);

        DateInterval screeningDate = new DateInterval(screeningDateStart,screeningDateEnd);

        dateIntervals.add(screeningDate);

        List<Movie> movies = StreamSupport
                .stream(movieService.findAll().spliterator(), false)
                .collect(Collectors.toList());

        List<Screening> screeningList = StreamSupport
                .stream(screeningService.findAll().spliterator(), false)
                .filter(s -> s.getRoomName() == roomName)
                .collect(Collectors.toList());

        for (Screening screening : screeningList
        ) {
            var optionalMovie = movies.stream().filter(m -> m.getName() == screening.getMovieName()).findFirst();
            if(optionalMovie.isPresent())
            {
                var additionalScreeningDateStart= screening.getScreeningDate();
                additionalScreeningDateStart.setMinutes(screeningDateStart.getMinutes() + movie.getMovie_length());
                dateIntervals.add(new DateInterval(
                        additionalScreeningDateStart,
                        screening.addMinutes(10)));
            }
        }

        return !calendarEventOverlapHelper.isIntervalsClean(dateIntervals);
    }

    public void deleteScreening(String movieName)
    {
        if(userService.isLoggedInUserAdmin())
        {
            screeningService.deleteScreening(movieName);
        }
    }

    public List<Screening> getAllScreening()
    {
        return StreamSupport
                .stream(screeningService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
