package com.example.dynamodb.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.Movie;
import com.example.dynamodb.service.MovieSearchService;


@RestController
@RequestMapping("/movies")

public class DynamodDBController {
	
	

    private MovieSearchService movieSearchService;

    DynamodDBController(MovieSearchService movieSearchService) {
        this.movieSearchService = movieSearchService;
    }
	

	
	
	@RequestMapping(value = "/retrieveAllMovies", method = RequestMethod.GET)
  	public ResponseEntity<List<Movie>>  retrieveAllMovies() { 
		MultiValueMap<String, String> headers = new HttpHeaders();
		
		 Iterable<Movie> movies = movieSearchService.findAllMovies();
		 
		
		 if(movies != null) { 
			 System.out.println("Movies are not Null");
		 }
		
		 List<Movie> moviesList = StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toList());
		
		 movieSearchService.findMovieByParameter("test");
		 
		 
		 return new ResponseEntity<List<Movie>>(moviesList, headers,HttpStatus.OK);
	}

	
	
	
	@RequestMapping(value = "/retrieveMovieById/{filmId}", method = RequestMethod.GET)
  	public ResponseEntity<Movie>  retrieveMovie(@PathVariable(value="filmId", required = true) String filmId) { 
		MultiValueMap<String, String> headers = new HttpHeaders();
		Movie movie = movieSearchService.findByMovieId(filmId);
		return new ResponseEntity<Movie>(movie, headers,HttpStatus.OK);
	}

	
	

	
	
}
