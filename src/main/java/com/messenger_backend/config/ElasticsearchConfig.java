package com.messenger_backend.config;




import org.springframework.context.annotation.Configuration;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;



@Configuration
@EnableElasticsearchRepositories(basePackages = "com.messenger_backend.search.repo")  // Ensure this points to the correct package for repositories
public class ElasticsearchConfig {

	
	
}
