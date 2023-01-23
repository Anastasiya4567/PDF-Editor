package com.pdf.editor.application;

import com.pdf.editor.application.domain.*;
import com.pdf.editor.application.domain.token.Token;
import com.pdf.editor.interfaces.rest.requests.GenerateDocumentRequest;
import com.pdf.editor.misc.Converter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentEditionFacade {

    private final DocumentRepository documentRepository;

    private final GeneratedDocumentRepository generatedDocumentRepository;

    private final ImageRepository imageRepository;

    public DocumentEditionFacade(DocumentRepository documentRepository, GeneratedDocumentRepository generatedDocumentRepository, ImageRepository imageRepository) {
        this.documentRepository = documentRepository;
        this.generatedDocumentRepository = generatedDocumentRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void generatePDFFromSourceCode(GenerateDocumentRequest generateDocumentRequest) throws IOException {
        DocumentScanner documentScanner = new DocumentScanner();
        documentScanner.setSourceText(generateDocumentRequest.getSourceCode() + '\0');
        List<Token> tokens = documentScanner.scan();
        DocumentParser documentParser = new DocumentParser(imageRepository);
        documentParser.setTokens(tokens);
        PDDocument document = documentParser.parse(generateDocumentRequest.getId());

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<String> stringPages = new ArrayList<>();
        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            stringPages.add(Converter.imgToString(bufferedImage));
        }
        document.close();
        saveSourceCode(generateDocumentRequest.getId(), generateDocumentRequest.getSourceCode());
        saveGeneratedDocument(generateDocumentRequest.getId(), stringPages);
    }

    private void saveGeneratedDocument(String documentId, List<String> stringPages) {
        final var loaded = generatedDocumentRepository.loadByDocumentId(documentId);
        loaded.generatedPages = stringPages;
        generatedDocumentRepository.save(loaded);
    }

    public void saveSourceCode(String id, String sourceCode) {
        final var loaded = documentRepository.load(id);
        loaded.sourceCode = sourceCode;
        documentRepository.save(loaded);
    }
}
