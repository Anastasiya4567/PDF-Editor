package com.pdf.editor.application.mapper;

import com.pdf.editor.application.PDFDocumentDTO;
import com.pdf.editor.infrastructure.adapter.mongo.document.PDFDocument;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {
    PDFDocumentDTO mapFromDocument(final PDFDocument pdfDocument);
}
