package com.example.dynamodb.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.example.dto.Movie;

@EnableScan
public interface MovieRepository  extends CrudRepository<Movie,String> {

}
