package com.dh.catalog.repository;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CatalogSerieRepository extends MongoRepository<SerieServiceClient.SerieDto, String> {

    List<SerieServiceClient.SerieDto> findAllByGenre(String genre);
}
