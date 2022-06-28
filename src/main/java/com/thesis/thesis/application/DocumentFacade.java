package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.Token;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import com.thesis.thesis.infrastructure.port.DocumentPersistencePort;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DocumentFacade {

    private final DocumentPort documentPort;

    private final DocumentRepository documentRepository;

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

    public void generatePDFFromSourceText(PDFDocument pdfDocument) throws IOException {
        saveSourceCode(pdfDocument.id, pdfDocument.sourceCode);
        DocumentScannerFacade documentScannerFacade = new DocumentScannerFacade(pdfDocument.sourceCode);
        List<Token> tokens = documentScannerFacade.scan();
        DocumentParserFacade documentParserFacade = new DocumentParserFacade(tokens);
        PDDocument document = documentParserFacade.parse();
        // save generated pdf
    }

    @MongoTransactional
    public void saveSourceCode(String id, String sourceCode) {
        final var loaded = documentRepository.load(id);
        loaded.sourceCode = sourceCode;
        documentRepository.save(loaded);
    }
}
