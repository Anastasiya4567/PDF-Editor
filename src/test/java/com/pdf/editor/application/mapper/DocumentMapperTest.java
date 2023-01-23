package com.pdf.editor.application.mapper;

import com.pdf.editor.application.PDFDocumentDTO;
import com.pdf.editor.infrastructure.adapter.mongo.document.PDFDocument;
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
    }

    private PDFDocument createPDFDocument() {
        return new PDFDocument(TEST_ID, TEST_OWNER_EMAIL, TEST_PRIVATE_ACCESS, TEST_TITLE,
                TEST_SOURCE_CODE, TEST_CREATION_DATE);
    }

}
