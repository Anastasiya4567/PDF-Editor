package com.thesis.thesis.application.domain;

import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import com.thesis.thesis.infrastructure.port.GeneratedDocumentPersistencePort;
import com.thesis.thesis.infrastructure.port.ImagePersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentConfiguration {

    @Bean
    public DocumentRepository documentRepository(DocumentPersistencePort documentPersistencePort) {
        return new DocumentRepository(documentPersistencePort);
    }

    @Bean
    public GeneratedDocumentRepository generatedDocumentRepository(GeneratedDocumentPersistencePort generatedDocumentPersistencePort) {
        return new GeneratedDocumentRepository(generatedDocumentPersistencePort);
    }

    @Bean
    public ImageRepository imageRepository(ImagePersistencePort imagePersistencePort) {
        return new ImageRepository(imagePersistencePort);
    }

    @Bean
    public DocumentScanner documentScanner() {
        return new DocumentScanner();
    }

    @Bean
    public DocumentParser documentParser(ImageRepository imageRepository) {
        return new DocumentParser(imageRepository);
    }
}
