package com.thesis.thesis.infrastructure.port;

import com.thesis.thesis.infrastructure.adapter.mongo.GeneratedPDF;

import java.util.Optional;

public interface GeneratedDocumentPersistencePort {

    Optional<GeneratedPDF> findById(String id);

    GeneratedPDF save(GeneratedPDF generatedPDF);

    void deleteById(String generatedDocumentId);
}
