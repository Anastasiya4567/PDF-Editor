package com.pdf.editor.application.mapper;

import com.pdf.editor.application.GeneratedDocumentDTO;
import com.pdf.editor.infrastructure.adapter.mongo.document.GeneratedPDF;
import org.mapstruct.Mapper;

@Mapper
public interface GeneratedDocumentMapper {
    GeneratedDocumentDTO mapFromDocument(final GeneratedPDF generatedPDF);
}
