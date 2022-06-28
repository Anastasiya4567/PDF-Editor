package com.thesis.thesis.application.domain;

import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import io.vavr.control.Option;

public class DocumentRepository {

    private final DocumentPersistencePort documentPersistencePort;

    public DocumentRepository(DocumentPersistencePort documentPersistencePort) {
        this.documentPersistencePort = documentPersistencePort;
    }

    public PDFDocument load(String id) {
        return find(id).getOrElseThrow(
                () -> new RuntimeException("No document with id: " + id + " found"));
    }

    private Option<PDFDocument> find(String id) {
        return Option.ofOptional(documentPersistencePort.findById(id));
    }

    public void save(PDFDocument pdfDocument) {
        documentPersistencePort.save(pdfDocument);
    }
}

