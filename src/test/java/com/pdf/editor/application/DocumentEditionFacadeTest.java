package com.pdf.editor.application;

import com.pdf.editor.application.domain.DocumentPort;
import com.pdf.editor.application.domain.DocumentRepository;
import com.pdf.editor.application.domain.GeneratedDocumentRepository;
import com.pdf.editor.application.domain.ImageRepository;
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

    private ImageRepository imageRepository;

    @BeforeEach
    public void setup() {
        documentEditionFacade = createDocumentEditionFacade();
    }

    private DocumentEditionFacade createDocumentEditionFacade() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        return applicationConfig.documentEditionFacade(documentRepository, generatedDocumentRepository, imageRepository);
    }

}