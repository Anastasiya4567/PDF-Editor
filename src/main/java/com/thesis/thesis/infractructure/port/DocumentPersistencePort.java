package com.thesis.thesis.infractructure.port;

import com.thesis.thesis.infractructure.adapter.mongo.PDFDocument;

public interface DocumentPersistencePort {
    PDFDocument save(PDFDocument pdfDocument);
}
