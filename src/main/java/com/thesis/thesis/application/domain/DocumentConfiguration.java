package com.thesis.thesis.application.domain;

import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentConfiguration {

    @Bean
    public DocumentRepository documentRepository(DocumentPersistencePort documentPersistencePort) {
        return new DocumentRepository(documentPersistencePort);
    }
}
