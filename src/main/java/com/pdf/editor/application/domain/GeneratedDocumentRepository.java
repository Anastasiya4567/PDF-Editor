package com.pdf.editor.application.domain;

import com.pdf.editor.infrastructure.adapter.mongo.document.GeneratedPDF;
import com.pdf.editor.infrastructure.port.GeneratedDocumentPersistencePort;
import io.vavr.control.Option;

public class GeneratedDocumentRepository {

    private final GeneratedDocumentPersistencePort generatedDocumentPersistencePort;

    public GeneratedDocumentRepository(GeneratedDocumentPersistencePort generatedDocumentPersistencePort) {
        this.generatedDocumentPersistencePort = generatedDocumentPersistencePort;
    }

    public GeneratedPDF loadByDocumentId(String documentId) {
        return findByDocumentId(documentId).getOrElseThrow(
                () -> new RuntimeException("No generated document with document id: " + documentId + " found"));
    }

    private Option<GeneratedPDF> findByDocumentId(String documentId) {
        return Option.ofOptional(generatedDocumentPersistencePort.findByDocumentId(documentId));
    }

    public void save(GeneratedPDF loaded) {
        generatedDocumentPersistencePort.save(loaded);
    }

    public void deleteByDocumentId(String documentId) {
        generatedDocumentPersistencePort.deleteByDocumentId(documentId);
    }
}
