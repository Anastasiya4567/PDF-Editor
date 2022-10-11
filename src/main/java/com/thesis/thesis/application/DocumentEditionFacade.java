package com.thesis.thesis.application;

import com.thesis.thesis.misc.Converter;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import com.thesis.thesis.application.domain.Token;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentEditionFacade {

    private final DocumentRepository documentRepository;

    private final GeneratedDocumentRepository generatedDocumentRepository;

    public DocumentEditionFacade(DocumentRepository documentRepository, GeneratedDocumentRepository generatedDocumentRepository) {
        this.documentRepository = documentRepository;
        this.generatedDocumentRepository = generatedDocumentRepository;
    }

    @MongoTransactional
    public void generatePDFFromSourceText(PDFDocument pdfDocument) throws IOException {
        PDFDocument pdfDocumentToEdit = saveSourceCode(pdfDocument.id, pdfDocument.sourceCode);
        DocumentScannerFacade documentScannerFacade = new DocumentScannerFacade(pdfDocument.sourceCode);
        List<Token> tokens = documentScannerFacade.scan();
        DocumentParserFacade documentParserFacade = new DocumentParserFacade(tokens);
        PDDocument document = documentParserFacade.parse();
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<String> stringPages = new ArrayList();
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            stringPages.add(Converter.imgToString(bufferedImage));

//            ImageIOUtil.writeImage(
//                    bufferedImage, String.format("src/output/pdf-%d.%s", page + 1, extension), 300);
//            String stringImg = imgToString(bufferedImage, "pdf");
        }
        saveGeneratedDocument(pdfDocumentToEdit.generatedDocumentId, stringPages);
    }

    private void saveGeneratedDocument(String id, List<String> stringPages) {
        final var loaded = generatedDocumentRepository.load(id);
        loaded.generatedPages = stringPages;
        generatedDocumentRepository.save(loaded);
    }

    public PDFDocument saveSourceCode(String id, String sourceCode) {
        final var loaded = documentRepository.load(id);
        loaded.sourceCode = sourceCode;
        documentRepository.save(loaded);
        return loaded;
    }
}
