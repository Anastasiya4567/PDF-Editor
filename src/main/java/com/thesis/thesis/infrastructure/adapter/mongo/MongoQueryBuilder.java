package com.thesis.thesis.infrastructure.adapter.mongo;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoQueryBuilder {

    public Query buildQuery(String ownerEmail, String title) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("ownerEmail").is(ownerEmail),
                Criteria.where("title").regex(".*" + title + ".*")
        );
        Query query = new Query(criteria);
        query.fields().include("id", "title", "sourceCode", "creationDate", "generatedDocumentId");
        return query;
    }
}
