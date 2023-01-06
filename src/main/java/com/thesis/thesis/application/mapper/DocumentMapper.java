package com.thesis.thesis.application.mapper;

import com.thesis.thesis.application.PDFDocumentDTO;
import com.thesis.thesis.infrastructure.adapter.mongo.document.PDFDocument;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {
    PDFDocumentDTO mapFromDocument(final PDFDocument pdfDocument);
}
