package com.pdf.editor.application.domain;

import com.pdf.editor.infrastructure.adapter.mongo.document.PDFDocument;
import com.pdf.editor.infrastructure.port.DocumentPersistencePort;
import io.vavr.control.Option;

import java.util.List;

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

    public PDFDocument save(PDFDocument pdfDocument) {
        return documentPersistencePort.save(pdfDocument);
    }

    public List<PDFDocument> findAllDocuments() {
        return documentPersistencePort.findAll();
    }

    public void deleteById(String id) {
        documentPersistencePort.deleteById(id);
    }

    public PDFDocument findById(String id) {
        return Option.ofOptional(documentPersistencePort.findById(id)).getOrElseThrow(
                () -> new RuntimeException("No document with id: " + id + " found"));
    }

    public List<PDFDocument> findAllByOwnerEmail(String email) {
        return documentPersistencePort.findAllByOwnerEmail(email);
    }
}

