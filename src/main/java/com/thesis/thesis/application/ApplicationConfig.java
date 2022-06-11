package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DocumentFacade documentFacade(DocumentPort documentPort, DocumentPersistencePort documentPersistencePort) {
        return new DocumentFacade(documentPort, documentPersistencePort);
    }
}
