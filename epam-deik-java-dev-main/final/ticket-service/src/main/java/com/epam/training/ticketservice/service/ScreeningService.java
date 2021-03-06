package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScreeningService {

    private ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Transactional
    public void createScreening(String movieName, String roomName, Date screeningDate)
    {
        screeningRepository.save(new Screening(movieName, roomName, screeningDate));
    }

    @Transactional
    public void deleteScreening(String movieName, String roomName, Date screeningDate)
    {
        var screening = StreamSupport
                .stream(screeningRepository.findAll().spliterator(), false)
                .filter(s -> s.getMovieName().equals(movieName) &&
                        s.getRoomName().equals(roomName)  &&
                        s.getScreeningDate().getTime() == screeningDate.getTime())
                .findAny();

        if(screening.isPresent()) screeningRepository.delete(screening.get());
    }

    @Transactional
    public Iterable<Screening> findAll()
    {
        return screeningRepository.findAll();
    }
}
