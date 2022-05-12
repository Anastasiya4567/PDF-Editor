package com.thesis.thesis.application;

import com.thesis.thesis.infractructure.port.DocumentPort;
import org.springframework.data.domain.Page;

import java.util.UUID;

public class DocumentFacade {

    private final DocumentPort documentPort;

    public DocumentFacade(DocumentPort documentPort) {
        this.documentPort = documentPort;
    }

    public Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize) {
        return documentPort.getAllDocuments(pageIndex, pageSize);
    }

    public void addNewDocument(String title) {
        PDFDocumentDTO pdfDocumentDTO = new PDFDocumentDTO(UUID.randomUUID().toString(), title);
        documentPort.addNewDocument(pdfDocumentDTO);
    }
}
