package com.thesis.thesis.application;

import com.thesis.thesis.infractructure.port.DocumentPort;
import org.springframework.data.domain.Page;

public class DocumentFacade {

    private final DocumentPort documentPort;

    public DocumentFacade(DocumentPort documentPort) {
        this.documentPort = documentPort;
    }

    public Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize) {
        return documentPort.getAllDocuments(pageIndex, pageSize);
    }

    public void addNewDocument(PDFDocumentDTO pdfDocumentDTO) {
        documentPort.addNewDocument(pdfDocumentDTO);
    }
}
