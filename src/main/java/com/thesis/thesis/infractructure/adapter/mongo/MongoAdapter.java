package com.thesis.thesis.infractructure.adapter.mongo;

import com.thesis.thesis.application.PDFDocumentDTO;
import com.thesis.thesis.infractructure.port.DocumentPort;
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
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Query pagedQuery = new Query().with(pageable);
        List<PDFDocumentDTO> pdfDocumentDTOList = mongoTemplate.find(pagedQuery, PDFDocumentDTO.class, "pdfDocuments");
        return new PageImpl<>(pdfDocumentDTOList);
    }

    @Override
    public void addNewDocument(PDFDocumentDTO pdfDocumentDTO) {
        mongoTemplate.save(pdfDocumentDTO);
    }
}
