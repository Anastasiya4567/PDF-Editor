package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import com.thesis.thesis.application.domain.ImageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public DocumentFacade documentFacade(DocumentPort documentPort,
                                         DocumentRepository documentRepository,
                                         GeneratedDocumentRepository generatedDocumentRepository,
                                         ImageRepository imageRepository) {
        return new DocumentFacade(documentPort, documentRepository, generatedDocumentRepository, imageRepository);
    }

    @Bean
    public DocumentEditionFacade documentEditionFacade(
            DocumentRepository documentRepository,
            GeneratedDocumentRepository generatedDocumentRepository,
            ImageRepository imageRepository) {
        return new DocumentEditionFacade(documentRepository, generatedDocumentRepository, imageRepository);
    }
}
