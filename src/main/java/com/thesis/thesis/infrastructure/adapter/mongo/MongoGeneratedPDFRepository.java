package com.thesis.thesis.infrastructure.adapter.mongo;

import com.thesis.thesis.infrastructure.port.GeneratedDocumentPersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGeneratedPDFRepository extends MongoRepository<GeneratedPDF, String>, GeneratedDocumentPersistencePort {
}
