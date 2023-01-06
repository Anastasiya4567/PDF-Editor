package com.thesis.thesis.application.mapper;

import com.thesis.thesis.application.GeneratedDocumentDTO;
import com.thesis.thesis.infrastructure.adapter.mongo.document.GeneratedPDF;
import org.mapstruct.Mapper;

@Mapper
public interface GeneratedDocumentMapper {
    GeneratedDocumentDTO mapFromDocument(final GeneratedPDF generatedPDF);
}
