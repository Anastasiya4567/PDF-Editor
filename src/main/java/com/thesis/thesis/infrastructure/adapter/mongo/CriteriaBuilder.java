package com.thesis.thesis.infrastructure.adapter.mongo;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CriteriaBuilder {

    private List<Criteria> criteria = new ArrayList<>();

    CriteriaBuilder addPrivateAccess(Boolean privateAccess) {
        addCriteria("privateAccess", (criteria -> criteria.is(privateAccess)));
        return this;
    }

    private void addCriteria(String key, Function<Criteria, Criteria> addCriteriaOperator) {
        Criteria criteriaToAdd = addCriteriaOperator.apply(Criteria.where(key));
        criteria.add(criteriaToAdd);
    }

    CriteriaBuilder addOwnerEmail(String ownerEmail) {
        addCriteria("ownerEmail", (criteria -> criteria.is(ownerEmail)));
        return this;
    }

    CriteriaBuilder addTitle(String title) {
        addCriteria("title", (criteria -> criteria.regex(".*" + title + ".*")));
        return this;
    }

    Criteria getResult() {
        Criteria criteria = new Criteria();
        criteria.andOperator(this.criteria);
        return criteria;
    }
}
