package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.mapper.DocumentMapper;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DocumentFacade {

    private final DocumentPort documentPort;

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    public DocumentFacade(DocumentPort documentPort, DocumentRepository documentRepository) {
        this.documentPort = documentPort;
        this.documentRepository = documentRepository;
    }

    public Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize) {
        return documentPort.getAllDocuments(pageIndex, pageSize);
    }

    public void addNewDocument(String title) {
        PDFDocument pdfDocument = new PDFDocument(UUID.randomUUID().toString(), title);
        documentRepository.save(pdfDocument);
    }

    public PDFDocumentDTO getDocumentByTitle(String title) {
        return documentMapper.mapFromDocument(documentRepository.findByTitle(title));
    }

    public boolean checkIfUnique(String title) {
        List<PDFDocument> documents = documentRepository.findAllDocuments();
        return !documents.stream()
                .map(document -> document.title)
                .collect(Collectors.toList())
                .contains(title);
    }

    public void deleteDocumentById(String id) {
        System.out.println(id);
        documentRepository.deleteById(id);
    }
}
