package com.thesis.thesis.infrastructure.port;

import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;

public interface DocumentPersistencePort {
    PDFDocument save(PDFDocument pdfDocument);
}
