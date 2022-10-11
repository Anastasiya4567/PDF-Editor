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

    public Page<PDFDocumentDTO> getFilteredDocuments(int pageIndex, int pageSize, String title) {
        return documentPort.getFilteredDocuments(pageIndex, pageSize, title);
    }

    public void addNewDocument(String title) {
        PDFDocument pdfDocument = new PDFDocument(UUID.randomUUID().toString(), title);
        documentRepository.save(pdfDocument);
    }

    public PDFDocumentDTO getDocumentByTitle(String title) {
        return documentMapper.mapFromDocument(documentRepository.findByTitle(title));
    }

    public boolean isUnique(String title) {
        List<PDFDocument> documents = documentRepository.findAllDocuments();
        boolean isUnique = !(documents.stream()
                .map(document -> document.title)
                .collect(Collectors.toList())
                .contains(title));
//        System.out.println(title);
//        System.out.println(documents.stream()
//                .map(document -> document.title)
//                .collect(Collectors.toList()));
//        System.out.println(documents.stream()
//                .map(document -> document.title)
//                .collect(Collectors.toList()).contains(title));
        if (isUnique) {
            addNewDocument(title);
        }
        return isUnique;
    }

    public void deleteDocumentById(String id) {
        documentRepository.deleteById(id);
    }
}
