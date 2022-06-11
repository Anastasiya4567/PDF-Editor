package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import org.springframework.data.domain.Page;

import java.util.UUID;

public class DocumentFacade {

    private final DocumentPort documentPort;

    private final DocumentPersistencePort documentPersistencePort;

    public DocumentFacade(DocumentPort documentPort, DocumentPersistencePort documentPersistencePort) {
        this.documentPort = documentPort;
        this.documentPersistencePort = documentPersistencePort;
    }

    public Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize) {
        return documentPort.getAllDocuments(pageIndex, pageSize);
    }

    public void addNewDocument(String title) {
        PDFDocument pdfDocument = new PDFDocument(UUID.randomUUID().toString(), title);
        documentPersistencePort.save(pdfDocument);
    }
}
