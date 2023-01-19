package com.pdf.editor.infrastructure.port;

import com.pdf.editor.infrastructure.adapter.mongo.document.GeneratedPDF;

import java.util.Optional;

public interface GeneratedDocumentPersistencePort {

    Optional<GeneratedPDF> findByDocumentId(String documentId);

    GeneratedPDF save(GeneratedPDF generatedPDF);

    void deleteByDocumentId(String documentId);
}
