package com.pdf.editor.infrastructure.adapter.mongo;

import com.pdf.editor.application.PDFDocumentDTO;
import com.pdf.editor.application.domain.DocumentPort;
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
    public Page<PDFDocumentDTO> getFilteredDocuments(String ownerEmail, int pageIndex, int pageSize, String title) {
        MongoQueryBuilder mongoQueryBuilder = new MongoQueryBuilder();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        List<PDFDocumentDTO> pdfDocumentDTOList = getPageResult(ownerEmail, pageable, mongoQueryBuilder, title);
        long totalNumberOfElements = getTotalNumberOfElements(ownerEmail, mongoQueryBuilder, title);
        return new PageImpl(pdfDocumentDTOList, pageable, totalNumberOfElements);
    }

    private List<PDFDocumentDTO> getPageResult(String ownerEmail, Pageable pageable, MongoQueryBuilder mongoQueryBuilder, String title) {
        Query pagedQuery = mongoQueryBuilder.buildQuery(ownerEmail, title).with(pageable);
        return mongoTemplate.find(pagedQuery, PDFDocumentDTO.class, "pdfDocuments");
    }

    private long getTotalNumberOfElements(String ownerEmail, MongoQueryBuilder mongoQueryBuilder, String title) {
        Query unpagedQueryForCountingPurposes = mongoQueryBuilder.buildQuery(ownerEmail, title);
        return mongoTemplate.count(unpagedQueryForCountingPurposes, PDFDocumentDTO.class, "pdfDocuments");
    }
}
