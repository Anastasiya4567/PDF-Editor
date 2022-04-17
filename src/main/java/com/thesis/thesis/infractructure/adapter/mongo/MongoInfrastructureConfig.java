package com.thesis.thesis.infractructure.adapter.mongo;

import org.elasticsearch.node.NodeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.imageio.IIOException;
import java.io.File;

@EnableMongoRepositories
@EnableElasticsearchRepositories
// add (basePackages = "...")
@Configuration
public class MongoInfrastructureConfig {

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
