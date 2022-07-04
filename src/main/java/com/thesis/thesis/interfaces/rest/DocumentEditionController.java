package com.thesis.thesis.interfaces.rest;

import com.thesis.thesis.application.DocumentEditionFacade;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DocumentEditionController {

    private final DocumentEditionFacade documentEditionFacade;

    public DocumentEditionController(DocumentEditionFacade documentEditionFacade) {
        this.documentEditionFacade = documentEditionFacade;
    }

    @RequestMapping(value = "/generateFromSourceText", method = RequestMethod.POST)
    public ResponseEntity<?> generatePDFFromSourceText(@RequestBody PDFDocument pdfDocument) {
        try {
            documentEditionFacade.generatePDFFromSourceText(pdfDocument);
            return ResponseEntity.ok("The document has generated");
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: " + exception.getMessage());
        }
    }
}
