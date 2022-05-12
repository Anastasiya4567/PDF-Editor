package com.thesis.thesis.infractructure.adapter.mongo;


import org.springframework.data.mongodb.core.query.Query;

public class MongoQueryBuilder {

    public Query buildQuery() {
        Query query = new Query();
        query.fields().include("id", "title");
        return query;
    }
}