package com.thesis.thesis.infrastructure.port;

import com.thesis.thesis.infrastructure.adapter.mongo.document.Image;

import java.util.List;
import java.util.Optional;

public interface ImagePersistencePort {

    List<Image> findAllByDocumentId(String id);

    Image save(Image image);

    Optional<Image> findByDocumentIdAndName(String documentId, String name);
}
