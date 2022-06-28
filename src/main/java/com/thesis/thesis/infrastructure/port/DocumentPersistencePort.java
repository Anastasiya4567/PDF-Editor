package com.thesis.thesis.infrastructure.port;

import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;

import java.util.Optional;

public interface DocumentPersistencePort {
    Optional<PDFDocument> findById(String id);

    PDFDocument save(PDFDocument pdfDocument);
}
