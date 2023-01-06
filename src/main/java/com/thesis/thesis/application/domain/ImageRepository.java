package com.thesis.thesis.application.domain;

import com.thesis.thesis.infrastructure.adapter.mongo.document.Image;
import com.thesis.thesis.infrastructure.port.ImagePersistencePort;
import io.vavr.control.Option;

import java.util.List;

public class ImageRepository {

    private final ImagePersistencePort imagePersistencePort;

    public ImageRepository(ImagePersistencePort imagePersistencePort) {
        this.imagePersistencePort = imagePersistencePort;
    }

    public List<Image> findAllByDocumentId(String id) {
        return imagePersistencePort.findAllByDocumentId(id);
    }

    public Image save(Image image) {
        return imagePersistencePort.save(image);
    }

    public Option<Image> findByDocumentIdAndName(String documentId, String name) {
        return Option.ofOptional(imagePersistencePort.findByDocumentIdAndName(documentId, name));
    }

    public Image load(String documentId, String name) {
        return findByDocumentIdAndName(documentId, name).getOrElseThrow(
                () -> new RuntimeException("No image with document id: " + documentId + " and name: " + name + " found"));
    }
}
