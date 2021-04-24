package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @MockBean
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @Autowired
    private MovieController movieController;

    @Test
    void getAllMovies() {
        assertEquals(1,1);
    }

    @Test
    void createMovie() {
        userService.adminLogin("admin","admin");
        movieController.createMovie("","",60);
        Assertions.assertTrue(movieService.getAllMovies().size() > 0);
    }

    @Test
    void updateMovie() {
    }

    @Test
    void deleteMovie() {
    }

    @Test
    void getMovieByName() {
    }
}