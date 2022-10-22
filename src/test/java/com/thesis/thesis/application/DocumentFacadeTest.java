package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DocumentFacadeTest {

    @Mock
    private DocumentPort documentPort;

    private DocumentFacade documentFacade;

    private DocumentRepository documentRepository;

    private GeneratedDocumentRepository generatedDocumentRepository;

    @BeforeEach
    public void setup() {
        documentFacade = createDocumentFacade();
    }

    // tests nothing
    @Test
    void getFilteredDocuments() {
        // given
        int pageIndex = 0;
        int pageSize = 5;
        String title = "story";

        List<PDFDocumentDTO> pdfDocumentDTOs = List.of(
                new PDFDocumentDTO("test id 1", "A story", "", OffsetDateTime.now(), "test generated id 1"),
                new PDFDocumentDTO("test id 2", "A story of TB", "", OffsetDateTime.now(), "test generated id 2")
        );

        Page<PDFDocumentDTO> filteredDocumentsPage = new PageImpl<>(pdfDocumentDTOs, PageRequest.of(pageIndex, pageSize), 2);

        given(documentPort.getFilteredDocuments(pageIndex, pageSize, title)).willReturn(filteredDocumentsPage);

        // when
        var resultPage = documentFacade.getFilteredDocuments(pageIndex, pageSize, title);

        // then
        assertThat(resultPage.getContent()).isEqualTo(pdfDocumentDTOs);
        assertThat(resultPage.getContent().size()).isEqualTo(2);
        assertThat(resultPage.getPageable().getPageSize()).isEqualTo(5);
        assertThat(resultPage.getPageable().getPageNumber()).isEqualTo(0);
    }

    private DocumentFacade createDocumentFacade() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        return applicationConfig.documentFacade(documentPort, documentRepository, generatedDocumentRepository);
    }


}
