package com.pdf.editor.integration;

import com.pdf.editor.PdfEditorApplication;
import com.pdf.editor.application.DocumentFacade;
import com.pdf.editor.application.PDFDocumentDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = PdfEditorApplication.class)
@Testcontainers
@SpringBootTest
@DirtiesContext
class DocumentIntegrationTest {

    private static final OffsetDateTime TEST_DATE = OffsetDateTime.now();

    private static final String TEST_SOURCE_CODE = "\\title Test";

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.8");

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DocumentFacade documentFacade;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @AfterEach
    void cleanUp() {
        mongoTemplate.remove(new Query(), PDFDocumentDTO.class);
    }

    @ParameterizedTest(name = "Filter documents by title")
    @ValueSource(strings = "The")
    void filterDocumentsByTitle(String title) {
        // given
        createAndSaveDocuments();

        // when
        Page<PDFDocumentDTO> filteredDocuments =
                documentFacade.getFilteredDocuments("test@gmail.com", 0, 5, title);

        // then
        assertThat(filteredDocuments.getContent()).hasSize(2);
    }

    @ParameterizedTest(name = "Filter documents by owner email")
    @ValueSource(strings = "test3@gmail.com")
    void filterDocumentsByOwnerEmail(String ownerEmail) {
        // given
        createAndSaveDocuments();

        // when
        Page<PDFDocumentDTO> filteredDocuments =
                documentFacade.getFilteredDocuments(ownerEmail, 0, 5, "");

        // then
        assertThat(filteredDocuments.getContent()).hasSize(1);
    }

    @ParameterizedTest(name = "Check document paging")
    @ValueSource(strings = {"test@gmail.com"})
    void checkDocumentPaging(String ownerEmail) {
        // given
        createAndSaveDocuments();

        // when
        Page<PDFDocumentDTO> filteredDocuments =
                documentFacade.getFilteredDocuments(ownerEmail, 0, 2, "");

        // then
        assertThat(filteredDocuments.getContent()).hasSize(2);
    }

    private void createAndSaveDocuments() {
        PDFDocumentDTO firstDocument = createPDFDocumentDTO("test id 1", "The first document", "test@gmail.com", true);
        mongoTemplate.save(firstDocument, "pdfDocuments");
        PDFDocumentDTO secondDocument = createPDFDocumentDTO("test id 2", "The second document", "test2@gmail.com", false);
        mongoTemplate.save(secondDocument, "pdfDocuments");
        PDFDocumentDTO thirdDocument = createPDFDocumentDTO("test id 3", "New document", "test@gmail.com", true);
        mongoTemplate.save(thirdDocument, "pdfDocuments");
    }

    private PDFDocumentDTO createPDFDocumentDTO(String id, String title, String ownerEmail, Boolean privateAccess) {
        return new PDFDocumentDTO(id, title, ownerEmail, privateAccess, TEST_SOURCE_CODE, TEST_DATE);
    }

}
