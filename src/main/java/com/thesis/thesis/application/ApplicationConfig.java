package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DocumentFacade documentFacade(DocumentPort documentPort, DocumentRepository documentRepository, GeneratedDocumentRepository generatedDocumentRepository) {
        return new DocumentFacade(documentPort, documentRepository, generatedDocumentRepository);
    }

    @Bean
    public DocumentEditionFacade documentEditionFacade(DocumentRepository documentRepository, GeneratedDocumentRepository generatedDocumentRepository) {
        return new DocumentEditionFacade(documentRepository, generatedDocumentRepository);
    }
}
