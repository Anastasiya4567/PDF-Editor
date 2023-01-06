package com.thesis.thesis.application.mapper;

import com.thesis.thesis.application.PDFDocumentDTO;
import com.thesis.thesis.infrastructure.adapter.mongo.document.PDFDocument;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentMapperTest {
    private static final String TEST_ID = "test id";
    private static final String TEST_OWNER_EMAIL = "test owner email";
    private static final boolean TEST_PRIVATE_ACCESS = false;
    private static final String TEST_TITLE = "test title";
    private static final String TEST_SOURCE_CODE = "";
    private static final OffsetDateTime TEST_CREATION_DATE = OffsetDateTime.now();
    private static final String TEST_GENERATED_DOCUMENT_ID = "test generated id";
    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    @Test
    void mapDocument() {
        // given
        PDFDocument pdfDocument = createPDFDocument();

        // when
        PDFDocumentDTO result = documentMapper.mapFromDocument(pdfDocument);

        // then
        assertThat(result.id).isEqualTo(TEST_ID);
        assertThat(result.ownerEmail).isEqualTo(TEST_OWNER_EMAIL);
        assertThat(result.privateAccess).isEqualTo(TEST_PRIVATE_ACCESS);
        assertThat(result.title).isEqualTo(TEST_TITLE);
        assertThat(result.sourceCode).isEmpty();
        assertThat(result.creationDate).isEqualTo(TEST_CREATION_DATE);
        assertThat(result.generatedDocumentId).isEqualTo(TEST_GENERATED_DOCUMENT_ID);
    }

    private PDFDocument createPDFDocument() {
        return new PDFDocument(TEST_ID, TEST_OWNER_EMAIL, TEST_PRIVATE_ACCESS, TEST_TITLE,
                TEST_SOURCE_CODE, TEST_CREATION_DATE, TEST_GENERATED_DOCUMENT_ID);
    }

}
