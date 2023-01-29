package com.pdf.editor.infrastructure.port;

import com.pdf.editor.infrastructure.adapter.mongo.document.PDFDocument;

import java.util.List;
import java.util.Optional;

public interface DocumentPersistencePort {
    Optional<PDFDocument> findById(String id);

    PDFDocument save(PDFDocument pdfDocument);

    List<PDFDocument> findAll();

    void deleteById(String id);

    List<PDFDocument> findAllByOwnerEmail(String email);
}
