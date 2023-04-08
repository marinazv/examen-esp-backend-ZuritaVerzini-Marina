package com.dh.movie.service;


import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {


    private final MovieRepository movieRepository;

    private NewMovieEventProducer newMovieEventProducer;
    public MovieService(MovieRepository movieRepository, NewMovieEventProducer newMovieEventProducer) {
        this.movieRepository = movieRepository;
        this.newMovieEventProducer = newMovieEventProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie save(Movie movie) {
        Movie movieCreada = movieRepository.save(movie);
        NewMovieEventProducer.Data data= new NewMovieEventProducer.Data();
        data.setId(String.valueOf(movieCreada.getId()));
        data.setName(movieCreada.getName());
        data.setGenre(movieCreada.getGenre());
        data.setUrlStream(movieCreada.getUrlStream());
        newMovieEventProducer.publishNewMoviePublish(data);
        return movieCreada;
    }
}
