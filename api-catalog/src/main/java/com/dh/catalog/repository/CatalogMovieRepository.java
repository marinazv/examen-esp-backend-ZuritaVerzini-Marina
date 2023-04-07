package com.dh.catalog.repository;

import com.dh.catalog.client.MovieServiceClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogMovieRepository extends MongoRepository<MovieServiceClient.MovieDto, String> {

    List<MovieServiceClient.MovieDto> findAllByGenre(String genre);
}
