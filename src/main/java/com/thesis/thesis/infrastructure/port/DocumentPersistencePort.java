package com.thesis.thesis.infrastructure.port;

import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;

import java.util.List;
import java.util.Optional;

public interface DocumentPersistencePort {
    Optional<PDFDocument> findById(String id);

    PDFDocument save(PDFDocument pdfDocument);

    Optional<PDFDocument> findByTitle(String title);

    List<PDFDocument> findAll();

    void deleteById(String id);
}
