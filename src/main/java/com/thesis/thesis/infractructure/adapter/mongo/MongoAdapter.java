package com.thesis.thesis.infractructure.adapter.mongo;

import com.thesis.thesis.application.PDFDocumentDTO;
import com.thesis.thesis.application.domain.DocumentPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoAdapter implements DocumentPort {

    private final MongoTemplate mongoTemplate;

    public MongoAdapter(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize) {
        MongoQueryBuilder mongoQueryBuilder = new MongoQueryBuilder();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<PDFDocumentDTO> pdfDocumentDTOList = getPageResult(pageable, mongoQueryBuilder);
        long totalNumberOfElements = getTotalNumberOfElements(mongoQueryBuilder);
        return new PageImpl(pdfDocumentDTOList, pageable, totalNumberOfElements);
    }

    private List<PDFDocumentDTO> getPageResult(Pageable pageable, MongoQueryBuilder mongoQueryBuilder) {
        Query pagedQuery = mongoQueryBuilder.buildQuery().with(pageable);
        return mongoTemplate.find(pagedQuery, PDFDocumentDTO.class, "pdfDocuments");
    }

    private long getTotalNumberOfElements(MongoQueryBuilder mongoQueryBuilder) {
        Query unpagedQueryForCountingPurposes =mongoQueryBuilder.buildQuery();
        return mongoTemplate.count(unpagedQueryForCountingPurposes, PDFDocumentDTO.class, "pdfDocuments");
    }
}
