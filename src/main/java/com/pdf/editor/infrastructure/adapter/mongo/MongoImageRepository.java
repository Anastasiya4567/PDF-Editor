package com.pdf.editor.infrastructure.adapter.mongo;

import com.pdf.editor.infrastructure.adapter.mongo.document.Image;
import com.pdf.editor.infrastructure.port.ImagePersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoImageRepository extends MongoRepository<Image, String>, ImagePersistencePort {

}
