package com.example.dynamodb.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.dynamodb.repositories")
public class DynamoDBConfig {
/**
    @Bean
    public AmazonDynamoDB amazonDynamoDB(@Value("${aws.access-key}") String accessKey,
                                            @Value("${aws.secret-key}") String secretKey,
                                            @Value("${aws.dynamodb.endpoint}") String endpoint) {
       return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, null))
                .build();
    }
    
    
    **/
    
    @Bean
    public AmazonDynamoDB amazonDynamoDB(@Value("${aws.access-key}") String accessKey,
                                            @Value("${aws.secret-key}") String secretKey
               
    		) {
    	
    	
    	  AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,
       		   secretKey);
    	  
    	  AmazonDynamoDB dynamoDB =  AmazonDynamoDBClientBuilder
                .standard().withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    	  
    	  return dynamoDB;
       
     
       /**
       final AmazonDynamoDB client = AmazonDynamoDBClient.builder().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
**/
    }

}