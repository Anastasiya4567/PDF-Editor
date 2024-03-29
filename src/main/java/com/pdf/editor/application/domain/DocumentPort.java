package com.pdf.editor.application.domain;

import com.pdf.editor.application.PDFDocumentDTO;
import org.springframework.data.domain.Page;

public interface DocumentPort {
    Page<PDFDocumentDTO> getFilteredDocuments(String ownerEmail, int pageIndex, int pageSize, String title);
}
