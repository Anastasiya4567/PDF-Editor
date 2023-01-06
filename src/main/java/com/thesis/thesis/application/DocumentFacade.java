package com.thesis.thesis.application;

import com.thesis.thesis.application.domain.DocumentPort;
import com.thesis.thesis.application.domain.DocumentRepository;
import com.thesis.thesis.application.domain.GeneratedDocumentRepository;
import com.thesis.thesis.application.domain.ImageRepository;
import com.thesis.thesis.application.mapper.DocumentMapper;
import com.thesis.thesis.application.mapper.GeneratedDocumentMapper;
import com.thesis.thesis.infrastructure.adapter.mongo.document.GeneratedPDF;
import com.thesis.thesis.infrastructure.adapter.mongo.document.Image;
import com.thesis.thesis.infrastructure.adapter.mongo.document.PDFDocument;
import com.thesis.thesis.misc.Converter;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.LogManager.getLogger;

public class DocumentFacade {

    private final Logger log = getLogger(this.getClass());

    private final DocumentPort documentPort;

    private final DocumentRepository documentRepository;

    private final GeneratedDocumentRepository generatedDocumentRepository;

    private final ImageRepository imageRepository;

    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    private final GeneratedDocumentMapper generatedDocumentMapper = Mappers.getMapper(GeneratedDocumentMapper.class);

    public DocumentFacade(DocumentPort documentPort, DocumentRepository documentRepository, GeneratedDocumentRepository generatedDocumentRepository, ImageRepository imageRepository) {
        this.documentPort = documentPort;
        this.documentRepository = documentRepository;
        this.generatedDocumentRepository = generatedDocumentRepository;
        this.imageRepository = imageRepository;
    }

    public Page<PDFDocumentDTO> getFilteredDocuments(String ownerEmail, int pageIndex, int pageSize, String title) {
        return documentPort.getFilteredDocuments(ownerEmail, pageIndex, pageSize, title);
    }

    @Transactional
    public void addNewDocument(String ownerEmail, String title, Boolean privateAccess) throws IOException {
        String generatedDocumentId = UUID.randomUUID().toString();
        OffsetDateTime creationDate = OffsetDateTime.now();
        PDFDocument pdfDocument = new PDFDocument(UUID.randomUUID().toString(), ownerEmail, privateAccess, title, "", creationDate, generatedDocumentId);
        documentRepository.save(pdfDocument);
        GeneratedPDF generatedPDF = generateBlankPDF(generatedDocumentId);
        generatedDocumentRepository.save(generatedPDF);
    }

    private GeneratedPDF generateBlankPDF(String generatedDocumentId) throws IOException {
        PDDocument document = new PDDocument();
        PDPage blankPage = new PDPage();
        document.addPage(blankPage);

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
        List<String> stringPages = List.of(Converter.imgToString(bufferedImage));
        document.close();
        return new GeneratedPDF(generatedDocumentId, stringPages);
    }

    public PDFDocumentDTO getDocumentById(String id) {
        return documentMapper.mapFromDocument(documentRepository.findById(id));
    }

    public boolean isUniqueAmongOwners(String title, String ownerEmail) {
        List<PDFDocument> documents = documentRepository.findAllDocuments();
        return !(documents.stream()
                .filter(document -> Objects.equals(document.ownerEmail, ownerEmail))
                .map(document -> document.title)
                .collect(Collectors.toList())
                .contains(title));
    }

    public boolean isUniqueAmongAll(String title) {
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

    public void renameDocument(String newTitle, String id) {
        PDFDocument load = documentRepository.findById(id);
        load.title = newTitle;
        documentRepository.save(load);
    }

    public GeneratedDocumentDTO getGeneratedDocumentById(String id) {
        GeneratedPDF load = generatedDocumentRepository.load(id);
        return generatedDocumentMapper.mapFromDocument(load);
    }

    public List<Image> getImagesById(String documentId) {
        return imageRepository.findAllByDocumentId(documentId);
    }

    @Transactional
    public void addNewImage(Image image, String documentId) {
        image.documentId = documentId;
        imageRepository.save(image);
    }
}
