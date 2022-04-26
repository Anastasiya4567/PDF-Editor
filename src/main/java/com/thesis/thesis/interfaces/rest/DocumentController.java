package com.thesis.thesis.interfaces.rest;

import com.thesis.thesis.application.DocumentFacade;
import com.thesis.thesis.application.PDFDocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DocumentController {

    private final DocumentFacade documentFacade;

    public DocumentController(DocumentFacade documentFacade) {
        this.documentFacade = documentFacade;
    }

    @RequestMapping(value = "/documents/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public Page<PDFDocumentDTO> getAllDocuments(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
        return documentFacade.getAllDocuments(pageIndex, pageSize);
    }

    @RequestMapping(value = "/newDocument", method = RequestMethod.POST)
    public void addNewDocument(@RequestBody PDFDocumentDTO pdfDocumentDTO) {
        documentFacade.addNewDocument(pdfDocumentDTO);
    }

}
