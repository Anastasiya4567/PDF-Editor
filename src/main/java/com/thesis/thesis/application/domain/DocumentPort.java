package com.thesis.thesis.application.domain;

import com.thesis.thesis.application.PDFDocumentDTO;
import org.springframework.data.domain.Page;

public interface DocumentPort {
    Page<PDFDocumentDTO> getFilteredDocuments(int pageIndex, int pageSize, String title);
}
