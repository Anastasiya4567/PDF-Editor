package com.pdf.editor.infrastructure.adapter.mongo;

import com.pdf.editor.infrastructure.adapter.mongo.document.GeneratedPDF;
import com.pdf.editor.infrastructure.port.GeneratedDocumentPersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGeneratedPDFRepository extends MongoRepository<GeneratedPDF, String>, GeneratedDocumentPersistencePort {
}
