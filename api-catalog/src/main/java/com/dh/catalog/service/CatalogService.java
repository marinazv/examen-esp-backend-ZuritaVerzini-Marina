package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.event.NewMovieEventConsumer;
import com.dh.catalog.event.NewSerieEventConsumer;
import com.dh.catalog.repository.CatalogMovieRepository;
import com.dh.catalog.repository.CatalogSerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CatalogService {

    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;
    private final CatalogMovieRepository catalogMovieRepository;

    private final CatalogSerieRepository catalogSerieRepository;

    public CatalogService(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, CatalogMovieRepository catalogMovieRepository, CatalogSerieRepository catalogSerieRepository) {
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
        this.catalogMovieRepository =catalogMovieRepository;
        this.catalogSerieRepository = catalogSerieRepository;
    }

    @CircuitBreaker(name= "getMovieByGenre", fallbackMethod="getMovieByGenreFallbackValue")
    @Retry(name="getMovieByGenre")
    public List<MovieServiceClient.MovieDto> getMovieByGenre(String genre){
        return movieServiceClient.getMovieByGenre(genre);
    }

    @CircuitBreaker(name= "getSerieByGenre", fallbackMethod="getSerieByGenreFallbackValue")
    @Retry(name="getSerieByGenre")
    public List<SerieServiceClient.SerieDto> getSerieByGenre(String genre){
        return serieServiceClient.getByGenre(genre);
    }

    private List<MovieServiceClient.MovieDto> getMovieByGenreFallbackValue(String genre, Throwable t) throws Exception{
           return catalogMovieRepository.findAllByGenre(genre);
    }

    private List<SerieServiceClient.SerieDto> getSerieByGenreFallbackValue(String genre, Throwable t) throws Exception{
        return catalogSerieRepository.findAllByGenre(genre);
    }

    public List<MovieServiceClient.MovieDto> getMovieByGenreOffline(String genre) {
        return catalogMovieRepository.findAllByGenre(genre);
    }

    public List<SerieServiceClient.SerieDto> getSerieByGenreOffline(String genre){
        return catalogSerieRepository.findAllByGenre(genre);
    }

    public void saveMovie(NewMovieEventConsumer.Data message){
        try{
        MovieServiceClient.MovieDto newMovie = new MovieServiceClient.MovieDto();
        newMovie.setId(Long.parseLong(message.getId()));
        newMovie.setGenre(message.getGenre());
        newMovie.setName(message.getName());
        newMovie.setUrlStream(message.getUrlStream());
        catalogMovieRepository.save(newMovie);
        }catch (Exception error){
            log.error(error.getMessage());
        }
    }
    public void saveSerie(NewSerieEventConsumer.Data message){
        SerieServiceClient.SerieDto newSerie = new SerieServiceClient.SerieDto();
        newSerie.setGenre(message.getGenre());
        newSerie.setName(message.getName());
        catalogSerieRepository.save(newSerie);
    }


}
