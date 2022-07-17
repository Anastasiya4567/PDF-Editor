package com.thesis.thesis.application;

import com.thesis.thesis.misc.Converter;
import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import com.thesis.thesis.application.mapper.DocumentMapper;
import com.thesis.thesis.application.mapper.GeneratedDocumentMapper;
import com.thesis.thesis.infrastructure.adapter.mongo.GeneratedPDF;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DocumentFacade {

    private final DocumentPort documentPort;

    private final DocumentRepository documentRepository;

    private final GeneratedDocumentRepository generatedDocumentRepository;

    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    private final GeneratedDocumentMapper generatedDocumentMapper = Mappers.getMapper(GeneratedDocumentMapper.class);

    public DocumentFacade(DocumentPort documentPort, DocumentRepository documentRepository, GeneratedDocumentRepository generatedDocumentRepository) {
        this.documentPort = documentPort;
        this.documentRepository = documentRepository;
        this.generatedDocumentRepository = generatedDocumentRepository;
    }

    public Page<PDFDocumentDTO> getAllDocuments(int pageIndex, int pageSize, String title) {
        return documentPort.getAllDocuments(pageIndex, pageSize, title);
    }

    public void addNewDocument(String title) throws IOException {
        String generatedDocumentId = UUID.randomUUID().toString();
        PDFDocument pdfDocument = new PDFDocument(UUID.randomUUID().toString(), title, "", generatedDocumentId);
        documentRepository.save(pdfDocument);
        GeneratedPDF generatedPDF = generateBlankPDF(generatedDocumentId);
        generatedDocumentRepository.save(generatedPDF);
    }

    private GeneratedPDF generateBlankPDF(String generatedDocumentId) throws IOException {
        PDDocument document = new PDDocument();
        PDPage blankPage = new PDPage();
        document.addPage(blankPage);

        // ***
//        PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
//        contentStream.beginText();
//        contentStream.newLineAtOffset(10, 100);
//        contentStream.showText("Hello");
//        contentStream.endText();
//        contentStream.close();
        // ***
        document.close();

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
        List<String> stringPages = List.of(Converter.imgToString(bufferedImage));
        return new GeneratedPDF(generatedDocumentId, stringPages);
    }


    public PDFDocumentDTO getDocumentByTitle(String title) {
        return documentMapper.mapFromDocument(documentRepository.findByTitle(title));
    }

    public boolean isUnique(String title) throws IOException {
        List<PDFDocument> documents = documentRepository.findAllDocuments();
        boolean isUnique = !(documents.stream()
                .map(document -> document.title)
                .collect(Collectors.toList())
                .contains(title));
        if (isUnique) {
            addNewDocument(title);
        }
        return isUnique;
    }

    public void deleteDocumentById(String id) {
        documentRepository.deleteById(id);
    }

    public GeneratedDocumentDTO getGeneratedDocumentById(String id) {
        GeneratedPDF load = generatedDocumentRepository.load(id);
        return generatedDocumentMapper.mapFromDocument(load);
    }
}
