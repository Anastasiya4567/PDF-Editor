package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.Token;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

public class DocumentEditionFacade {

    private final DocumentRepository documentRepository;

    public DocumentEditionFacade(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
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
