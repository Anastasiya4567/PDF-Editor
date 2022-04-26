package com.thesis.thesis.infractructure.adapter.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableElasticsearchRepositories
// add (basePackages = "...")
@Configuration
public class MongoInfrastructureConfig {

    @Bean
    public MongoAdapter mongoAdapter(MongoTemplate mongoTemplate) {
        return new MongoAdapter(mongoTemplate);
    }

//    @Bean
//    NodeBuilder nodeBuilder() {
//        return new NodeBuilder();
//    }
//
//    @Bean
//    ElasticsearchOperations elasticsearchOperations() {
//        File file = null;
//        try {
//            file = File.createTempFile("temp-elastic", Long.toString(System.nanoTime()));
//        } catch (IIOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
