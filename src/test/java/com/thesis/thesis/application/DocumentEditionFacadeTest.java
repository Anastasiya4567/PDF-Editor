package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DocumentEditionFacadeTest {

    @Mock
    private DocumentPort documentPort;

    private DocumentEditionFacade documentEditionFacade;

    private DocumentRepository documentRepository;

    private GeneratedDocumentRepository generatedDocumentRepository;

    @BeforeEach
    public void setup() {
        documentEditionFacade = createDocumentEditionFacade();
    }

    private DocumentEditionFacade createDocumentEditionFacade() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        return applicationConfig.documentEditionFacade(documentRepository, generatedDocumentRepository);
    }

}
