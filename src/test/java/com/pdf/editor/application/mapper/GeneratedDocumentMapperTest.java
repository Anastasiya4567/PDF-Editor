package com.pdf.editor.application.mapper;

import com.pdf.editor.application.GeneratedDocumentDTO;
import com.pdf.editor.infrastructure.adapter.mongo.document.GeneratedPDF;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneratedDocumentMapperTest {
    private static final String TEST_ID = "test id";

    private static final String TEST_DOCUMENT_ID = "test document id";
    private static final List<String> TEST_GENERATED_PAGES = List.of();
    private final GeneratedDocumentMapper generatedDocumentMapper = Mappers.getMapper(GeneratedDocumentMapper.class);

    @Test
    void mapDocument() {
        // given
        GeneratedPDF pdfDocument = new GeneratedPDF(TEST_ID, TEST_DOCUMENT_ID, TEST_GENERATED_PAGES);

        // when
        GeneratedDocumentDTO result = generatedDocumentMapper.mapFromDocument(pdfDocument);

        // then
        assertThat(result.id).isEqualTo(TEST_ID);
        assertThat(result.generatedPages).isEmpty();
    }
}
