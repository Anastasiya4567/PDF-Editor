package com.thesis.thesis.interfaces.rest;

import com.thesis.thesis.application.DocumentFacade;
import com.thesis.thesis.application.GeneratedDocumentDTO;
import com.thesis.thesis.application.PDFDocumentDTO;
import com.thesis.thesis.infrastructure.adapter.mongo.PDFDocument;
import com.thesis.thesis.misc.MessageResponse;
import kernel.security.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DocumentController {

    private final UserRepository userRepository;

    private final DocumentFacade documentFacade;

    public DocumentController(UserRepository userRepository, DocumentFacade documentFacade) {
        this.userRepository = userRepository;
        this.documentFacade = documentFacade;
    }

    @GetMapping(value = "/documents/{pageIndex}/{pageSize}")
    public Page<PDFDocumentDTO> getFilteredDocuments(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize, @RequestParam(name = "title") String title) {
        return documentFacade.getFilteredDocuments(pageIndex, pageSize, title);
    }

    @PostMapping(value = "/add/{title}")
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

    @PostMapping(value = "/rename/{title}")
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

    @GetMapping(value = "/getDocumentByTitle")
    public PDFDocumentDTO getDocumentByTitle(@RequestParam(name = "title") String title) {
        return documentFacade.getDocumentByTitle(title);
    }

    @PostMapping(value = "/deleteDocument")
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

    @GetMapping(value = "/getGeneratedDocument")
    public GeneratedDocumentDTO getGeneratedDocument(@RequestParam(name = "id") String id) {
        return documentFacade.getGeneratedDocumentById(id);
    }

}
