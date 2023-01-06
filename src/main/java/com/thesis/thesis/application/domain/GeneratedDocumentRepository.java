package com.thesis.thesis.application.domain;

import com.thesis.thesis.infrastructure.adapter.mongo.document.GeneratedPDF;
import com.thesis.thesis.infrastructure.port.GeneratedDocumentPersistencePort;
import io.vavr.control.Option;

public class GeneratedDocumentRepository {

    private final GeneratedDocumentPersistencePort generatedDocumentPersistencePort;

    public GeneratedDocumentRepository(GeneratedDocumentPersistencePort generatedDocumentPersistencePort) {
        this.generatedDocumentPersistencePort = generatedDocumentPersistencePort;
    }

    public GeneratedPDF load(String id) {
        return find(id).getOrElseThrow(
                () -> new RuntimeException("No generated document with id: " + id + " found"));
    }

    private Option<GeneratedPDF> find(String id) {
        return Option.ofOptional(generatedDocumentPersistencePort.findById(id));
    }

    public void save(GeneratedPDF loaded) {
        generatedDocumentPersistencePort.save(loaded);
    }

    public void deleteById(String generatedDocumentId) {
        generatedDocumentPersistencePort.deleteById(generatedDocumentId);
    }
}
