package com.thesis.thesis.infractructure.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDocumentRepository extends MongoRepository<PDFDocument, String> {
}
