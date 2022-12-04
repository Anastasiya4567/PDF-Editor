package com.thesis.thesis.infrastructure.adapter.mongo;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoQueryBuilder {

    public Query buildQuery(String ownerEmail, String title) {

        Criteria criteriaForPrivateDocuments = new CriteriaBuilder()
                .addPrivateAccess(true)
                .addOwnerEmail(ownerEmail)
                .addTitle(title)
                .getResult();

        Criteria criteriaForPublicDocuments = new CriteriaBuilder()
                .addPrivateAccess(false)
                .addTitle(title)
                .getResult();

        List<Criteria> allCriteria = List.of(criteriaForPrivateDocuments, criteriaForPublicDocuments);

        Criteria criteria = new Criteria();
        criteria.orOperator(allCriteria);

        Query query = new Query(criteria);
        query.fields().include("id", "title", "sourceCode", "privateAccess", "creationDate", "generatedDocumentId");
        return query;
    }
}
