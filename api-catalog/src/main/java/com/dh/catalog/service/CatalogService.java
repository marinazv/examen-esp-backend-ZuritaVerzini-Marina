package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.repository.CatalogMovieRepository;
import com.dh.catalog.repository.CatalogSerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    //metodo offline
    private List<MovieServiceClient.MovieDto> getMovieByGenreFallbackValue(String genre, Throwable t) throws Exception{
           return catalogMovieRepository.findAllByGenre(genre);

    }
    //metodo offline
    private List<SerieServiceClient.SerieDto> getSerieByGenreFallbackValue(String genre, Throwable t) throws Exception{
        return catalogSerieRepository.findAllByGenre(genre);
    }
}
