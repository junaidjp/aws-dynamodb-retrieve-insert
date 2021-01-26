
package com.example.dynamodb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.example.dto.Movie;
import com.example.dynamodb.repositories.MovieRepository;

@Service
public class MovieSearchService {
	  @Autowired
	  private AmazonDynamoDB amazondynamoDB ;
	  
	  
	  
	  private MovieRepository MovieRepository;

	    public MovieSearchService(MovieRepository MovieRepository) {
	        this.MovieRepository = MovieRepository;
	    }

	    public List<Movie> findAllMovies() {
	        return StreamSupport.stream(MovieRepository.findAll().spliterator(), true).collect(Collectors.toList());
	    }

	    public Movie addNewMovie(Movie movie) {
	        return MovieRepository.save(movie);
	    }

	    public Movie findByMovieId(String movieId) {
	        return MovieRepository.findById(movieId).orElse(null);
	    }

	    
	    public Movie findMovieByParameterQuery(String value) { 
	    	
	    	Map<String, AttributeValue> key = new HashMap<String,AttributeValue>();
	    	AttributeValue attributeValue = new AttributeValue();
	    	attributeValue.setS("1");
	    	key.put("filmId", attributeValue);
			GetItemResult item = amazondynamoDB.getItem("movie", key);
	    	
			 Map<String,String> expressionAttributesNames = new HashMap<>();
			    expressionAttributesNames.put("#Year","Year");
			 
			    Map<String,AttributeValue> expressionAttributeValues = new HashMap<>();
			    expressionAttributeValues.put(":Year",new AttributeValue().withS("1998"));
			 
			    QueryRequest queryRequest = new QueryRequest()
			            .withTableName("movie")
			            .withKeyConditionExpression("#Year = :Year")
			            .withIndexName("YearIndex")
			            .withExpressionAttributeNames(expressionAttributesNames)
			            .withExpressionAttributeValues(expressionAttributeValues);
			 
			    QueryResult queryResult = amazondynamoDB.query(queryRequest);
			 
			    List<Map<String,AttributeValue>> attributeValues = queryResult.getItems();
			 
			    if(attributeValues.size()>0) {
			         attributeValues.get(0);
			    } else {
			        return null;
			    }
			
			HashMap<String, String> nameMap = new HashMap<String, String>();
	    	nameMap.put("#yr", "year");
	    	
	    	HashMap<String, Object> valueMap = new HashMap<String, Object>();
	    	valueMap.put(":yyyy", 1985);
	    	
	    	QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr= :yyyy").withNameMap(nameMap)
	    			.withValueMap(valueMap);
	    	
	    	
	    	return null;
	    }
	    
	    
	    
//Second Phase     
public Movie findMovieByParameterTwo(String value) { 
	    	
		
		DynamoDBMapper mapper = new DynamoDBMapper(amazondynamoDB);
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":yr", new AttributeValue().withS("1998"));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("ReleasedYear = :yr")
				.withExpressionAttributeValues(eav);
		List<Movie> scanResult = mapper.scan(Movie.class, scanExpression);

		System.out.println(scanResult);

		Movie movie = scanResult.get(0);
		return scanResult.get(0);
	    }



// Next Video directly loading from Mapper - First Phase 
	public Movie findMovieByParameter(String value) {

		DynamoDBMapper mapper = new DynamoDBMapper(amazondynamoDB);
		Movie movie = new Movie();
		movie.setFilmId("1");
		Movie result = null;
		try {
			result = mapper.load(movie);
		} catch (Exception e) {
			System.err.println("Unable to retrieve data: ");
			System.err.println(e.getMessage());
		}

		return result;
	}
}


