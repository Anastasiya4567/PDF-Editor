package com.pdf.editor.infrastructure.port;

import com.pdf.editor.infrastructure.adapter.mongo.document.Image;

import java.util.List;
import java.util.Optional;

public interface ImagePersistencePort {

    List<Image> findAllByDocumentId(String id);

    Image save(Image image);

    void deleteAllByDocumentId(String documentId);

    Optional<Image> findByDocumentIdAndName(String documentId, String name);
}
