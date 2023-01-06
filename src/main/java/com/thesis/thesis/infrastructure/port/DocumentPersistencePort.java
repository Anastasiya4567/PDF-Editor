package com.thesis.thesis.infrastructure.port;

import com.thesis.thesis.infrastructure.adapter.mongo.document.PDFDocument;

import java.util.List;
import java.util.Optional;

public interface DocumentPersistencePort {
    Optional<PDFDocument> findById(String id);

    PDFDocument save(PDFDocument pdfDocument);

    List<PDFDocument> findAll();

    void deleteById(String id);

}
