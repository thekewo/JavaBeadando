package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ScreeningServiceTest {

    @Mock
    ScreeningRepository screeningRepository;

    ScreeningService screeningService;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        screeningService = new ScreeningService(screeningRepository);
    }

    @Test
    void createScreening() {
        Date today = new Date();
        today.setHours(0);
        Screening screening = new Screening("m","r",today);
        List<Screening> screeningList = new ArrayList<>();
        screeningList.add(screening);

        when(screeningRepository.save(any(Screening.class))).thenReturn(screening);
        when(screeningRepository.findAll()).thenReturn(screeningList);

        screeningService.createScreening("m","r",today);
        var result = screeningService.findAll();

        Assert.assertEquals(screening, result.iterator().next());
    }

    @Test
    void deleteScreening() {
        Date today = new Date();
        today.setHours(0);
        Screening screening = new Screening("m","r",today);
        List<Screening> screeningList = new ArrayList<>();

        when(screeningRepository.save(any(Screening.class))).thenReturn(screening);
        when(screeningRepository.findAll()).thenReturn(screeningList);

        screeningService.createScreening("m","r",today);
        screeningService.deleteScreening(screening.getMovieName(),screening.getRoomName(),screening.getScreeningDate());
        var result = screeningService.findAll();

        assertTrue(result.spliterator().estimateSize() == 0);
    }

    @Test
    void findAll() {
        Date today = new Date();
        today.setHours(0);
        Screening screening = new Screening("m","r",today);
        Screening screening2 = new Screening("m","r",today);
        List<Screening> screeningList = new ArrayList<>();
        screeningList.add(screening);
        screeningList.add(screening2);

        when(screeningRepository.save(any(Screening.class))).thenReturn(screening);
        when(screeningRepository.findAll()).thenReturn(screeningList);

        screeningService.createScreening("m","r",today);
        var result = screeningService.findAll();

        assertTrue(result.spliterator().estimateSize() == 2);
        assertNotNull(result);
    }
}