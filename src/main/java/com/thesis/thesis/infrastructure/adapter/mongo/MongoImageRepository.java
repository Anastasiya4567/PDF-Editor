package com.thesis.thesis.infrastructure.adapter.mongo;

import com.thesis.thesis.infrastructure.adapter.mongo.document.Image;
import com.thesis.thesis.infrastructure.port.ImagePersistencePort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoImageRepository extends MongoRepository<Image, String>, ImagePersistencePort {

}
