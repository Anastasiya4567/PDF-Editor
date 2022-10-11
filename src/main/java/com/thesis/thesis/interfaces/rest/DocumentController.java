package com.thesis.thesis.interfaces.rest;

import com.thesis.thesis.application.DocumentFacade;
import com.thesis.thesis.application.GeneratedDocumentDTO;
import com.thesis.thesis.application.PDFDocumentDTO;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import com.thesis.thesis.misc.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DocumentController {

    private final DocumentFacade documentFacade;

    public DocumentController(DocumentFacade documentFacade) {
        this.documentFacade = documentFacade;
    }

    @RequestMapping(value = "/documents/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public Page<PDFDocumentDTO> getAllDocuments(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize, @RequestParam(name = "title") String title) {
        return documentFacade.getAllDocuments(pageIndex, pageSize, title);
    }

    @RequestMapping(value = "/add/{title}", method = RequestMethod.POST)
    public ResponseEntity<?> addNewDocument(@PathVariable("title") String title) {
        try {
            if (documentFacade.isUnique(title)) {
                documentFacade.addNewDocument(title);
                return ResponseEntity.ok(new MessageResponse("The document with title " + title + " has added"));
            }
            return ResponseEntity.ok(new MessageResponse("The document title is not unique!"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

    @RequestMapping(value = "/rename/{title}", method = RequestMethod.POST)
    public ResponseEntity<?> renameDocument(@PathVariable("title") String title, @RequestBody String id) {
        try {
            if (documentFacade.isUnique(title)) {
                documentFacade.renameDocument(title, id);
                return ResponseEntity.ok(new MessageResponse("The document with title " + title + " has added"));
            }
            return ResponseEntity.ok(new MessageResponse("The document title is not unique!"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

    @RequestMapping(value = "/getDocumentByTitle", method = RequestMethod.GET)
    public PDFDocumentDTO getDocumentByTitle(@RequestParam(name = "title") String title) {
        return documentFacade.getDocumentByTitle(title);
    }

    @RequestMapping(value = "/deleteDocument", method = RequestMethod.POST)
    public ResponseEntity<?> deleteDocument(@RequestBody PDFDocument pdfDocument) {
        try {
            documentFacade.deleteDocument(pdfDocument);
            return ResponseEntity.ok(new MessageResponse("The document deleted"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

    @RequestMapping(value = "/getGeneratedDocument", method = RequestMethod.GET)
    public GeneratedDocumentDTO getGeneratedDocument(@RequestParam(name = "id") String id) {
        return documentFacade.getGeneratedDocumentById(id);
    }

}
