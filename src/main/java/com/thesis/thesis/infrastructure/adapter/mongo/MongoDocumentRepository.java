package com.thesis.thesis.infrastructure.adapter.mongo;

import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDocumentRepository extends MongoRepository<PDFDocument, String>, DocumentPersistencePort {
}
