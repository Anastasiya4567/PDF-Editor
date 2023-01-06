package com.thesis.thesis.infrastructure.adapter.mongo;

import com.thesis.thesis.infrastructure.adapter.mongo.document.PDFDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoCreatedPDFDocumentsRepository extends MongoRepository<PDFDocument, String> {
    Optional<PDFDocument> findById(String id);
}
