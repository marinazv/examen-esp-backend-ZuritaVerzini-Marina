package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private CatalogService catalogService;


	public CatalogController(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@GetMapping("/movie/online/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getMoviesGenre(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.getMovieByGenre(genre));
	}

	@GetMapping("/series/online/{genre}")
	ResponseEntity<List<SerieServiceClient.SerieDto>> getSeriesGenre(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.getSerieByGenre(genre));
	}

	@GetMapping("/movie/offline/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getMoviesGenreOffline(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.getMovieByGenreOffline(genre));
	}

	@GetMapping("/series/offline/{genre}")
	ResponseEntity<List<SerieServiceClient.SerieDto>> getSeriesGenreOffline(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.getSerieByGenreOffline(genre));
	}

}
