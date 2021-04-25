package com.epam.training.ticketservice.controller;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovieControllerTest {

    @Mock
    MovieService movieService;
    @Mock
    MovieRepository movieRepository;

    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

    MovieController movieController;

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieService(movieRepository);
        userService = new UserService(userRepository);
        movieController = new MovieController(movieService,userService);
    }

    @Test
    void getAllMovies() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        Movie movie1 = new Movie("m1","t1",60);
        Movie movie2 = new Movie("m2","t2",60);
        Movie movie3 = new Movie("m3","t3",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie1);
        when(movieService.findAll()).thenReturn(movieList);
        when(movieRepository.findByName(any(String.class))).thenReturn(movie1);

        userService.adminLogin("admin","admin");
        movieController.createMovie("m1","t1",60);
        movieController.createMovie("m2","t2",60);
        movieController.createMovie("m3","t3",60);

        var result = movieController.getAllMovies();

        Assertions.assertTrue(movieService.getAllMovies().size() == 3);
        Assertions.assertEquals("m1",result.iterator().next().getName());
        Assertions.assertEquals("t1",result.iterator().next().getType());
        Assertions.assertEquals(60,result.iterator().next().getMovie_length());
    }

    @Test
    void createMovieWithAdmin() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);

        userService.adminLogin("admin","admin");
        movieController.createMovie("m1","t1",60);
        Assertions.assertTrue(movieService.getAllMovies().size() > 0);
    }

    @Test
    void dontCreateMovieWithoutAdmin() {
        User user = new User(1,"admin","admin",false,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",false,false));
        when(userRepository.getById(1)).thenReturn(user);

        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        when(movieRepository.save(any(Movie.class))).thenReturn(null);
        when(movieService.findAll()).thenReturn(movieList);

        userService.adminLogin("admin","admin");
        movieController.createMovie("m1","t1",60);
        Assertions.assertTrue(movieService.getAllMovies().size() == 0);
    }

    @Test
    void updateMovie() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);
        when(movieRepository.findByName(any(String.class))).thenReturn(movie);

        userService.adminLogin("admin","admin");
        movieController.updateMovie("m1","t1",60);

        var result = movieService.getAllMovies();

        Assertions.assertTrue(movieService.getAllMovies().size() > 0);
        Assertions.assertEquals("m1",result.iterator().next().getName());
        Assertions.assertEquals("t1",result.iterator().next().getType());
        Assertions.assertEquals(60,result.iterator().next().getMovie_length());
    }

    @Test
    void deleteMovie() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);
        when(movieRepository.findByName(any(String.class))).thenReturn(movie);

        userService.adminLogin("admin","admin");
        movieController.createMovie("m1","t1",60);
        movieController.deleteMovie("m1");
        Assertions.assertTrue(movieService.getAllMovies().size() == 0);
    }

    @Test
    void getMovieByName() {
        User user = new User(1,"admin","admin",true,true);
        when(userRepository.save(any(User.class))).thenReturn(new User(1,"admin","admin",true,false));
        when(userRepository.getById(1)).thenReturn(user);

        Movie movie = new Movie("m1","t1",60);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieService.findAll()).thenReturn(movieList);
        when(movieRepository.findByName(any(String.class))).thenReturn(movie);


        userService.adminLogin("admin","admin");
        movieController.createMovie("m1","t1",60);
        var result = movieController.getMovieByName("m1");
        Assertions.assertEquals(movie, result);
    }
}