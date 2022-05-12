package com.thesis.thesis.infractructure.adapter.mongo;

import com.thesis.thesis.infractructure.port.DocumentPersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDocumentRepository extends MongoRepository<PDFDocument, String>, DocumentPersistencePort {
}
