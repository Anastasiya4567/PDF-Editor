package com.thesis.thesis.interfaces.rest;

import com.thesis.thesis.application.DocumentEditionFacade;
import com.thesis.thesis.interfaces.rest.requests.GenerateDocumentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DocumentEditionController {

    private final DocumentEditionFacade documentEditionFacade;

    public DocumentEditionController(DocumentEditionFacade documentEditionFacade) {
        this.documentEditionFacade = documentEditionFacade;
    }

    @PostMapping(value = "/generateFromSourceCode")
    public ResponseEntity<?> generatePDFFromSourceCode(@RequestBody GenerateDocumentRequest generateDocumentRequest) {
        try {
            documentEditionFacade.generatePDFFromSourceCode(generateDocumentRequest);
            return ResponseEntity.ok("The document has generated");
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: " + exception.getMessage());
        }
    }
}
