package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import com.thesis.thesis.application.mapper.DocumentMapper;
import com.thesis.thesis.application.mapper.GeneratedDocumentMapper;
import com.thesis.thesis.infrastructure.adapter.mongo.GeneratedPDF;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import com.thesis.thesis.misc.Converter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.OffsetDateTime;
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

    public Page<PDFDocumentDTO> getFilteredDocuments(int pageIndex, int pageSize, String title) {
        return documentPort.getFilteredDocuments(pageIndex, pageSize, title);
    }

    @Transactional
    public void addNewDocument(String title) throws IOException {
        String generatedDocumentId = UUID.randomUUID().toString();
        OffsetDateTime creationDate = OffsetDateTime.now();
        PDFDocument pdfDocument = new PDFDocument(UUID.randomUUID().toString(), title, "", creationDate, generatedDocumentId);
        documentRepository.save(pdfDocument);
        GeneratedPDF generatedPDF = generateBlankPDF(generatedDocumentId);
        generatedDocumentRepository.save(generatedPDF);
    }

    private GeneratedPDF generateBlankPDF(String generatedDocumentId) throws IOException {
        PDDocument document = new PDDocument();
        PDPage blankPage = new PDPage();

        // ***
//        PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
//        contentStream.beginText();
//        contentStream.newLineAtOffset(10, 100);
//        contentStream.showText("Hello");
//        contentStream.endText();
//        contentStream.close();
        // ***
        document.addPage(blankPage);
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
        return !(documents.stream()
                .map(document -> document.title)
                .collect(Collectors.toList())
                .contains(title));
    }

    public void deleteDocument(PDFDocument pdfDocument) {
        generatedDocumentRepository.deleteById(pdfDocument.generatedDocumentId);
        documentRepository.deleteById(pdfDocument.id);
    }

    public GeneratedDocumentDTO getGeneratedDocumentById(String id) {
        GeneratedPDF load = generatedDocumentRepository.load(id);
        return generatedDocumentMapper.mapFromDocument(load);
    }

    public void renameDocument(String newTitle, String id) {
        PDFDocument load = documentRepository.findById(id);
        load.title = newTitle;
        documentRepository.save(load);
    }
}
