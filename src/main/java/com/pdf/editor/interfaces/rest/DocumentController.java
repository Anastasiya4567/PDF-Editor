package com.pdf.editor.interfaces.rest;

import com.pdf.editor.application.DocumentFacade;
import com.pdf.editor.application.GeneratedDocumentDTO;
import com.pdf.editor.interfaces.rest.requests.NewDocumentCreateRequest;
import com.pdf.editor.application.PDFDocumentDTO;
import com.pdf.editor.infrastructure.adapter.mongo.document.Image;
import com.pdf.editor.infrastructure.adapter.mongo.document.PDFDocument;
import com.pdf.editor.misc.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class DocumentController {

    private final DocumentFacade documentFacade;

    public DocumentController(DocumentFacade documentFacade) {
        this.documentFacade = documentFacade;
    }

    @GetMapping(value = "/user-documents/{pageIndex}/{pageSize}")
    public Page<PDFDocumentDTO> getFilteredDocuments(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize, @RequestParam(name = "title") String title) {
        String ownerEmail = getCurrentUserEmail();

        return documentFacade.getFilteredDocuments(ownerEmail, pageIndex, pageSize, title);
    }

    private String getCurrentUserEmail() {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return userDetails.getUsername();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewDocument(@RequestBody NewDocumentCreateRequest newDocumentCreateRequest) {

        String ownerEmail = getCurrentUserEmail();

        try {
            if (newDocumentCreateRequest.getPrivateAccess()) {
                if (documentFacade.isUniqueAmongOwners(newDocumentCreateRequest.getTitle(), ownerEmail)) {
                    documentFacade.addNewDocument(ownerEmail, newDocumentCreateRequest.getTitle(), newDocumentCreateRequest.getPrivateAccess());
                    return ResponseEntity.ok(new MessageResponse("The document with title " + newDocumentCreateRequest.getTitle() + " has added"));
                }
            } else {
                if (documentFacade.isUniqueAmongAll(newDocumentCreateRequest.getTitle())) {
                    documentFacade.addNewDocument(ownerEmail, newDocumentCreateRequest.getTitle(), newDocumentCreateRequest.getPrivateAccess());
                    return ResponseEntity.ok(new MessageResponse("The document with title " + newDocumentCreateRequest.getTitle() + " has added"));
                }
            }

            return ResponseEntity.ok(new MessageResponse("The document title " + newDocumentCreateRequest.getTitle() + " is not unique!"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

    @PostMapping(value = "/rename/{title}")
    public ResponseEntity<?> renameDocument(@PathVariable("title") String title, @RequestBody String id) {

        String ownerEmail = getCurrentUserEmail();

        try {
            if (documentFacade.isUniqueAmongOwners(title, ownerEmail)) {
                documentFacade.renameDocument(title, id);
                return ResponseEntity.ok(new MessageResponse("The document title changed to " + title));
            }
            return ResponseEntity.ok(new MessageResponse("The document title " + title + " is not unique!"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

    @GetMapping(value = "/getDocumentById")
    public PDFDocumentDTO getDocumentById(@RequestParam(name = "id") String id) {
        return documentFacade.getDocumentById(id);
    }

    @PostMapping(value = "/deleteDocument")
    public ResponseEntity<?> deleteDocument(@RequestBody PDFDocument pdfDocument) {
        try {
            documentFacade.deleteDocument(pdfDocument);
            return ResponseEntity.ok(new MessageResponse("The document " + pdfDocument.title + " deleted"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

    @GetMapping(value = "/getGeneratedDocument")
    public GeneratedDocumentDTO getGeneratedDocument(@RequestParam(name = "documentId") String documentId) {
        return documentFacade.getGeneratedDocumentByDocumentId(documentId);
    }

    @GetMapping(value = "/getImages")
    public List<Image> getImages(@RequestParam(name = "id") String id) {
        return documentFacade.getImagesById(id);
    }

    @PostMapping(value = "/addImage")
    public ResponseEntity<?> addImage(@RequestBody Image image, @RequestParam(name = "id") String id) {
        try {
            documentFacade.addNewImage(image, id);
            return ResponseEntity.ok(new MessageResponse("The image added"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }
    }

}
