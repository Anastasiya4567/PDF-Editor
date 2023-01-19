package com.pdf.editor.infrastructure.adapter.mongo;

import com.pdf.editor.infrastructure.adapter.mongo.document.PDFDocument;
import com.pdf.editor.infrastructure.port.DocumentPersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPDFDocumentRepository extends MongoRepository<PDFDocument, String>, DocumentPersistencePort {
}
